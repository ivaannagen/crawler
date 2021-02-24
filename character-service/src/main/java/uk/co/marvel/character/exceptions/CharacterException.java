package uk.co.marvel.character.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class CharacterException extends ResponseStatusException {
    public CharacterException(HttpStatus httpStatus, String reason) {super(httpStatus, reason);}
}
