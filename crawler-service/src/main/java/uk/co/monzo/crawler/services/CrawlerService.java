package uk.co.monzo.crawler.services;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import uk.co.monzo.crawler.client.JsoupClient;
import uk.co.monzo.crawler.repository.CrawlerRepository;

import java.util.*;
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

    public Map<String, Set<String>> refreshCache() {
        crawlerRepository.getCache().clear();
        return crawlerRepository.getCache();
    }

    public Map<String, Set<String>> fetchUrls(String url, int maxLevel) {
        return fetchUrls(url, url, 1, maxLevel);
    }


    private Map<String, Set<String>> fetchUrls(String url, String baseUrl,  HashMap<String, Set<String>> visited, int level, int maxLevel) {
        if (level <= maxLevel) {
            if (crawlerRepository.get(url).isEmpty()) {

                Set<String> nodesToVisit = fetchUrls(url);

                if (CollectionUtils.isNotEmpty(nodesToVisit)) {
                    crawlerRepository.create(url, nodesToVisit);
                    Map<String, Set<String>> visitedCache = crawlerRepository.getCache();
                    Set<String> nextLinks = new LinkedHashSet<>();
                    for (String nextLink : nodesToVisit) {
                        if (!visited.containsKey(nextLink) && isWithinSubdomain(nextLink, baseUrl)) {
                            nextLinks.add(nextLink);
                            fetchUrls(nextLink, baseUrl, level++, maxLevel);
                        }
                    }
                    visited.computeIfPresent(url, (k, v) -> nextLinks);
                }

            }
        }

        return visisted;
    }

    private boolean isWithinSubdomain(String url, String baseUrl) {
        String prefix = "www.";
        if (url.contains(prefix)) {
            return url.replace(prefix, StringUtils.EMPTY).startsWith(baseUrl.replace(prefix, StringUtils.EMPTY));
        }
        return url.startsWith(baseUrl.replace("www.", StringUtils.EMPTY));
    }


    private Set<String> fetchUrls(String url) {
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

}