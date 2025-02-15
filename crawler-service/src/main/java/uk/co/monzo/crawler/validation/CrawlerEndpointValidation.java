package uk.co.monzo.crawler.validation;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uk.co.monzo.crawler.configuration.CrawlerAppConfig;
import uk.co.monzo.crawler.exceptions.CrawlerException;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@Service
@Log4j2
public class CrawlerEndpointValidation {

    private final CrawlerAppConfig crawlerAppConfig;

    @Autowired
    public CrawlerEndpointValidation(CrawlerAppConfig crawlerAppConfig) {
        this.crawlerAppConfig = crawlerAppConfig;
    }

    public void validateUrl(String address) {
        try {
            new URL(address).toURI();
        }
        catch(MalformedURLException | URISyntaxException e) {
            throw new CrawlerException(HttpStatus.BAD_REQUEST, "Url to crawl is invalid");
        }
    }

    public void validateMaxLevel(int maxLevel) {
        if (maxLevel <= 0 || maxLevel > crawlerAppConfig.getMAX_LEVEL()) {
            throw new CrawlerException(HttpStatus.BAD_REQUEST, "Level to crawl is invalid");
        }
    }

}