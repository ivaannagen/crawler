package uk.co.monzo.crawler.services;

import lombok.extern.log4j.Log4j2;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uk.co.monzo.crawler.client.RequestBuilder;
import uk.co.monzo.crawler.client.ResponseHandler;
import uk.co.monzo.crawler.client.RestClient;
import uk.co.monzo.crawler.exceptions.CrawlerException;
import uk.co.monzo.crawler.repository.CrawlerRepository;

import java.io.IOException;
import java.util.*;

@Configuration
@PropertySource("classpath:application.yml")
@Service
@Log4j2
public class CrawlerService {

    @Autowired
    private RequestBuilder requestBuilder;

    @Autowired
    private ResponseHandler responseHandler;

    @Autowired
    private RestClient restClient;

    @Autowired
    private CrawlerRepository crawlerRepository;

    public HashMap<String, Set<String>> fetchUrls(String url, int maxLevel) {
        HashMap<String, Set<String>> visited = new HashMap<>();
        return fetchUrls(url, url, visited, 1, maxLevel);
    }


    public HashMap<String, Set<String>> fetchUrls(String url, String baseUrl, HashMap<String, Set<String>> visited, int level, int maxLevel) {

        if (level <= maxLevel) {

            Optional<Document> nodeOpt = getDocument2(url);

            if (nodeOpt.isPresent()) {
                Document node = nodeOpt.get();
                Set<String> visitedNodes = new LinkedHashSet<>();
                visited.putIfAbsent(url, visitedNodes);
                for (Element element : node.select("a[href]")) {
                    String nextLink = element.attr("abs:href");
                    if (!visited.containsKey(nextLink) && nextLink.startsWith(baseUrl)) {
                        visitedNodes.add(nextLink);
                        fetchUrls(nextLink, baseUrl, visited, level++, maxLevel);
                    }
                }
                visited.computeIfPresent(url, (k, v) -> visitedNodes);
            }

        }

        return visited;
    }

    public List<String> fetchUrls2(String url, int maxLevel) {
        List<String> visited = new LinkedList<>();
        return fetchUrls2(url, url, visited, 1, maxLevel);
    }

    private List<String> fetchUrls2(String url, String baseUrl, List<String> visited, int level, int maxLevel) {

        if (level <= maxLevel) {

            Optional<Document> nodeOpt = getDocument2(url);

            if (nodeOpt.isPresent()) {
                Document node = nodeOpt.get();
                visited.add(url);
                for (Element element : node.select("a[href]")) {
                    String nextLink = element.absUrl("href");
                    if (!visited.contains(nextLink) && nextLink.startsWith(baseUrl)) {
                        fetchUrls2(nextLink, baseUrl, visited, level++, maxLevel);
                    }
                }
            }

        }

        return visited;
    }

    private Optional<Document> getDocument2(String url) {
        try {
            Connection con = Jsoup.connect(url);
            Document node = con.get();
            int statusCode = con.response().statusCode();
            if(statusCode == 200) {
                return Optional.of(node);
            }
            else {
                log.error(new CrawlerException(HttpStatus.FAILED_DEPENDENCY, String.format("Could not parse Html node with status code [%s]", statusCode)));
            }
        }
        catch(IOException ioe) {
            log.error(new CrawlerException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("Could not parse Html node with path [%s]", ioe.getMessage())));
        }
        return Optional.empty();
    }

//    private MarvelResponse fetchCharacterIdsResponse(Integer limit, Integer offset) {
//        var charactersRequest = requestBuilder.buildGetCharactersRequest(limit, offset);
//        MarvelResponseContainer response = restClient.get(charactersRequest);
//        return Objects.nonNull(response) ? response.getData() : null;
//    }
}