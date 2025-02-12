package uk.co.monzo.crawler.exceptions;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorMessage {
    private final long timestamp;
    private final String message;
    private final String path;

}
