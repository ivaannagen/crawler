package uk.co.monzo.crawler.repository;

import org.springframework.stereotype.Component;

@Component
public class CrawlerRepository extends AbstractCRUD<Integer, String> {

    @Override
    public String create(Integer id, String url) {
        return super.create(id, url);
    }

}