package uk.co.allica.customer.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class CustomerException extends ResponseStatusException {
    public CustomerException(HttpStatus httpStatus, String reason) {super(httpStatus, reason);}
}
