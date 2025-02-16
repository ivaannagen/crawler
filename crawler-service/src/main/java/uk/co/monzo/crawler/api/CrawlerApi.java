package uk.co.monzo.crawler.api;

import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Set;

public interface CrawlerApi {

    ResponseEntity<Map<String, Set<String>>> getVisited(String address, Integer maxLevel);

    ResponseEntity<Map<String, Set<String>>> refreshCrawler();
}