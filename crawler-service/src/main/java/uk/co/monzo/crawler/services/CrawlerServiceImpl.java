package uk.co.monzo.crawler.services;

import lombok.extern.log4j.Log4j2;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
import java.util.stream.Collectors;

@Configuration
@PropertySource("classpath:application.yml")
@Service
@Log4j2
public class CrawlerServiceImpl {

    @Autowired
    private RequestBuilder requestBuilder;

    @Autowired
    private ResponseHandler responseHandler;

    @Autowired
    private RestClient restClient;

    @Autowired
    private CrawlerRepository crawlerRepository;

    private final String baseUrl = "https://monzo.com";

    private final List<String> urls = new ArrayList<>();

    private final Map<String, Set<String>> urlsMap = new HashMap<>();


    public Set<String> fetchUrls(String url, int level, int maxLevel) {

        if (level <= maxLevel) {

            Optional<Document> nodeOpt = getDocument(url);

            if (nodeOpt.isPresent()) {
                Document node = nodeOpt.get();
                urlsMap.put(url, Set.of());
                Elements elements = node.select("a[href]");

                for (Element element : elements) {
                    String nextLink = element.absUrl("href");
                    if (nextLink.startsWith(baseUrl) && !urlsMap.containsKey(nextLink)) {
                        fetchUrls(nextLink, level++, maxLevel);
                    }
                }
            }

        }

        return urlsMap.keySet();
    }

    public List<String> fetchUrls2(String url, int level, int maxLevel) {

        if (level <= maxLevel) {

            Optional<Document> nodeOpt = getDocument2(url);

            if (nodeOpt.isPresent()) {
                Document node = nodeOpt.get();
                for (Element element : node.select("a[href]")) {
                    String nextLink = element.absUrl("href");
                    if (!urls.contains(nextLink) && nextLink.startsWith(baseUrl)) {
                        fetchUrls2(nextLink, level++, maxLevel);
                    }
                }
            }

        }

        return urls;
    }

    private Optional<Document> getDocument(String url) {
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

    private Optional<Document> getDocument2(String url) {
        try {
            Connection con = Jsoup.connect(url);
            Document node = con.get();
            int statusCode = con.response().statusCode();
            if(statusCode == 200) {
                urls.add(url);
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