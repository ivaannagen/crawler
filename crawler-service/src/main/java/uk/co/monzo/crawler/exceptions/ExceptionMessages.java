package uk.co.monzo.crawler.exceptions;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessages {

    public static final String EXTERNAL_SERVICE_EXCEPTION_MESSAGE = "Cannot reach external upstream service: [%s]";
    public static final String CRAWLER_EXCEPTION_MESSAGE = "Failed to retrieve crawler data with message: [%s]";

}