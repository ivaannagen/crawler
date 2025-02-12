package uk.co.monzo.crawler.endpoint;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uk.co.monzo.crawler.exceptions.CrawlerException;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@Service
@Log4j2
public class CrawlerEndpointValidation {

    public void validateUrl(String address) {
        try {
            new URL(address).toURI();
        }
        catch(MalformedURLException | URISyntaxException e) {
            throw new CrawlerException(HttpStatus.BAD_REQUEST, "Url to crawl is invalid");
        }
    }

    public void validateLevel(int maxLevel) {
        if (maxLevel <= 0 || maxLevel > 5) {
            throw new CrawlerException(HttpStatus.BAD_REQUEST, "Level to crawl is invalid");
        }
    }

}