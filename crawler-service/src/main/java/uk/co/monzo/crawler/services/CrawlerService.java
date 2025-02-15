package uk.co.monzo.crawler.services;

import lombok.extern.log4j.Log4j2;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import uk.co.monzo.crawler.client.JsoupClient;
import uk.co.monzo.crawler.repository.CrawlerRepository;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@PropertySource("classpath:application.yml")
@Service
@Log4j2
public class CrawlerService {

    private final JsoupClient jsoupClient;

    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    private final CrawlerRepository crawlerRepository;

    @Autowired
    public CrawlerService(JsoupClient jsoupClient, CrawlerRepository crawlerRepository) {
        this.jsoupClient = jsoupClient;
        this.crawlerRepository = crawlerRepository;
    }

    public HashMap<String, Set<String>> fetchUrls(String url, int maxLevel) {
        HashMap<String, Set<String>> visited = new HashMap<>();
        return fetchUrls(url, url, visited, 1, maxLevel);
    }


    private HashMap<String, Set<String>> fetchUrls(String url, String baseUrl, HashMap<String, Set<String>> visited, int level, int maxLevel) {

        if (level <= maxLevel) {

            executor.submit(new CrawlerWorker(jsoupClient));
            

            Optional<Document> nodeOpt = jsoupClient.getDocument(url);

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

}