package uk.co.monzo.crawler.repository;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CrawlerRepository extends AbstractCRUD<String, Set<String>> {
}