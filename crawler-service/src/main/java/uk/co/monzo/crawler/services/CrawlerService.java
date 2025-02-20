package uk.co.monzo.crawler.services;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import uk.co.monzo.crawler.client.JsoupClient;
import uk.co.monzo.crawler.repository.CrawlerRepository;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Configuration
@PropertySource("classpath:application.yml")
@Service
@Log4j2
public class CrawlerService {

    private final JsoupClient jsoupClient;

    private final CrawlerRepository crawlerRepository;

    private final BlockingQueue<String> queue = new LinkedBlockingQueue();

    private final AtomicInteger counter = new AtomicInteger(1);

    private final ExecutorService executor = Executors.newFixedThreadPool(10);

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
        fetchUrlsDriver(url, maxLevel);
        return crawlerRepository.getCache();
    }

    public void fetchUrlsDriver(String url, int maxLevel) {
        try {
            queue.put(url);
            while (true) {
                try {
                    new CrawlerWorker(1, maxLevel, queue);
                } catch (InterruptedException ie) {
                    System.out.println(ie.getMessage());
                }
            }
        } catch (InterruptedException ie) {
            System.out.println(ie.getMessage());
        }
    }

    Set<String> fetchUrlsToVisit(String url) {
        Optional<Document> nodeOpt = jsoupClient.getDocument(url);

        if (nodeOpt.isPresent()) {
            Document node = nodeOpt.get();
            Elements elements = node.select("a[href]");
            return elements.stream()
                    .map(nextLink -> nextLink.attr("abs:href"))
                    .collect(Collectors.toSet());
        }
        return Set.of();
    }

    private Map<String, Set<String>> crawl(String url, String baseUrl, HashMap<String, Set<String>> visited, int level, int maxLevel) {
        if (level <= maxLevel) {

            Optional<Set<String>> visitedNodes = crawlerRepository.get(url);
            Set<String> nodesToVisit;
            if (visitedNodes.isPresent()) {
                nodesToVisit = visitedNodes.get();
            } else {
                nodesToVisit = fetchUrlsToVisit(url);
            }

            if (CollectionUtils.isNotEmpty(nodesToVisit)) {
                visited.putIfAbsent(url, nodesToVisit);
                Set<String> nextLinks = new LinkedHashSet<>();
                for (String nextLink : nodesToVisit) {
                    if (!visited.containsKey(nextLink) && nextLink.startsWith(baseUrl)) {
                        nextLinks.add(nextLink);
                        crawl(nextLink, baseUrl, visited, level++, maxLevel);
                    }
                }
                visited.computeIfPresent(url, (k, v) -> nextLinks);
                crawlerRepository.create(url, nextLinks);
            }

        }

        return visited;
    }

    class CrawlerWorker {

        private BlockingQueue<String> queue;

        private int level;

        private final int maxLevel;

        CrawlerWorker(int level, int maxLevel, BlockingQueue<String> queue) throws InterruptedException {
            this.level = level;
            this.maxLevel = maxLevel;
            this.queue = queue;
            run();
        }

        public void run() throws InterruptedException {
            String baseUrl;
            baseUrl = queue.take();
            ConcurrentHashMap<String, Set<String>> cache = crawlerRepository.getCache();
            if(cache.size() == 10) {
                return;
            }

            Set<String> nodesToVisit = fetchUrlsToVisit(baseUrl);
            if (CollectionUtils.isNotEmpty(nodesToVisit)) {
                cache.putIfAbsent(baseUrl, nodesToVisit);
                Set<String> nextLinks = new LinkedHashSet<>();
                for (String nextLink : nodesToVisit) {
                    if (!cache.containsKey(nextLink) && nextLink.startsWith(baseUrl)) {
                        nextLinks.add(nextLink);
                        queue.add(nextLink);
                    }
                }
                cache.computeIfPresent(baseUrl, (k, v) -> nextLinks);
            }
        }
    }

}