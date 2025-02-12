package uk.co.monzo.crawler.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class CrawlerException extends ResponseStatusException {
    public CrawlerException(HttpStatus httpStatus, String reason) {super(httpStatus, reason);}
}
