package uk.co.marvel.character.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ExternalServiceException extends ResponseStatusException {
    public ExternalServiceException(HttpStatus httpStatus, String reason) {super(httpStatus, reason);}
}
