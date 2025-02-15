package uk.co.monzo.crawler.services;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import uk.co.monzo.crawler.client.JsoupClient;
import uk.co.monzo.crawler.repository.CrawlerRepository;

import java.util.*;
import java.util.stream.Collectors;

@Configuration
@PropertySource("classpath:application.yml")
@Service
@Log4j2
public class CrawlerService {

    private final JsoupClient jsoupClient;

    // private final ExecutorService executor = Executors.newFixedThreadPool(10);

    private final CrawlerRepository crawlerRepository;

    @Autowired
    public CrawlerService(JsoupClient jsoupClient, CrawlerRepository crawlerRepository) {
        this.jsoupClient = jsoupClient;
        this.crawlerRepository = crawlerRepository;
    }

    public Map<String, Set<String>> refreshCache() {
        crawlerRepository.getCache().clear();
        return crawlerRepository.getCache();
    }

    public Map<String, Set<String>> fetchUrls(String url, int maxLevel) {
        return fetchUrls(url, url, new HashMap<>(), 1, maxLevel);
    }


    private Map<String, Set<String>> fetchUrls(String url, String baseUrl, HashMap<String, Set<String>> visited, int level, int maxLevel) {
        if (level <= maxLevel) {

            Optional<Set<String>> visitedNodes = crawlerRepository.get(url);
            Set<String> nodesToVisit;
            if(visitedNodes.isPresent()) {
                nodesToVisit = visitedNodes.get();
            }
            else {
                nodesToVisit = fetchUrls(url);
            }

            if (CollectionUtils.isNotEmpty(nodesToVisit)) {
                visited.putIfAbsent(url, nodesToVisit);
                Set<String> nextLinks = new LinkedHashSet<>();
                for (String nextLink : nodesToVisit) {
                    if (!visited.containsKey(nextLink) && nextLink.startsWith(baseUrl)) {
                        nextLinks.add(nextLink);
                        fetchUrls(nextLink, baseUrl, visited, level++, maxLevel);
                    }
                }
                visited.computeIfPresent(url, (k, v) -> nextLinks);
                crawlerRepository.create(url, nextLinks);
            }

        }

        return visited;
    }

    private Set<String> fetchUrls(String url) {
        Optional<Document> nodeOpt = jsoupClient.getDocument(url);

        if (nodeOpt.isPresent()) {
            Document node = nodeOpt.get();

            return node.select("a[href]").stream()
                    .map(nextLink -> nextLink.attr("abs:href"))
                    .collect(Collectors.toSet());
        }
        return Set.of();
    }


//    private void submitCrawlerWorkers(String url) {
//        executor.execute(new CrawlerWorker(url));
//    }

}