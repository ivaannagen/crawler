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

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

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

    public Map<String, Set<String>> fetchUrls(String url, int maxLevel) {
        return fetchUrls(url, url, 1, maxLevel);
    }


    private Map<String, Set<String>> fetchUrls(String url, String baseUrl, int level, int maxLevel) {
        if (level <= maxLevel) {
            Set<String> visitedNodes = fetchUrls(url);

            if (CollectionUtils.isNotEmpty(visitedNodes)) {
                crawlerRepository.create(url, visitedNodes);
                Map<String, Set<String>> visited = crawlerRepository.getCache();
                Set<String> nextLinks = new LinkedHashSet<>();
                for (String nextLink : visitedNodes) {
                    if (!visited.containsKey(nextLink) && nextLink.startsWith(baseUrl)) {
                        nextLinks.add(nextLink);
                        fetchUrls(nextLink, baseUrl, level++, maxLevel);
                    }
                }
                visited.computeIfPresent(url, (k, v) -> nextLinks);
            }

        }

        return crawlerRepository.getCache();
    }

//    private HashMap<String, Set<String>> fetchUrls(String url, String baseUrl, HashMap<String, Set<String>> visited, int level, int maxLevel) {
//
//        if (level <= maxLevel) {
//
//            executor.submit(new CrawlerWorker(jsoupClient));
//
//
//            Optional<Document> nodeOpt = jsoupClient.getDocument(url);
//
//            if (nodeOpt.isPresent()) {
//                Document node = nodeOpt.get();
//
//                Set<String> visitedNodes = new LinkedHashSet<>();
//                visited.putIfAbsent(url, visitedNodes);
//
//                for (Element element : node.select("a[href]")) {
//                    String nextLink = element.attr("abs:href");
//
//                    if (!visited.containsKey(nextLink) && nextLink.startsWith(baseUrl)) {
//                        nextLinks.add(nextLink);
//                        fetchUrls(nextLink, baseUrl, level++, maxLevel);
//                    }
//                }
//                visited.computeIfPresent(url, (k, v) -> nextLinks);
//            }
//
//        }
//
//        return visited;
//    }


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
//
//    private class CrawlerWorker implements Runnable {
//
//        private final String url;
//
//        CrawlerWorker(String url) {
//            this.url = url;
//        }
//
//        @Override
//        public void run() {
//            Set<String> visited = fetchUrls(url);
//            if(CollectionUtils.isNotEmpty(visited)) {
//                crawlerRepository.create(url, fetchUrls(url));
//            }
//        }
//    }

}