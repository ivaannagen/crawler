package uk.co.marvel.character.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
@Log4j2
public class CharacterExceptionHandler {

    @ExceptionHandler(value = ExternalServiceException.class)
    public ResponseEntity<ErrorMessage> handleExternalServiceException(HttpServletRequest request, ExternalServiceException ese) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(getErrorMessageForException(request.getRequestURI(),
                        String.format(ExceptionMessages.EXTERNAL_SERVICE_EXCEPTION_MESSAGE, ese.getReason())));

    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> handleeConstraintViolationException(HttpServletRequest request, HttpServletResponse response, ConstraintViolationException cve) {
        return ResponseEntity.status(response.getStatus())
                .body(getErrorMessageForException(request.getRequestURI(),
                        String.format(ExceptionMessages.CHARACTER_EXCEPTION_MESSAGE, cve.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList()))));

    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentTypeMismatchException(HttpServletRequest request, HttpServletResponse response, MethodArgumentTypeMismatchException matme) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(getErrorMessageForException(request.getRequestURI(),
                        String.format(ExceptionMessages.CHARACTER_EXCEPTION_MESSAGE, matme.getMessage() + matme.getParameter())));

    }

    @ExceptionHandler(value = CharacterException.class)
    public ResponseEntity<ErrorMessage> handleCharacterException(HttpServletRequest request, CharacterException ce) {
        return ResponseEntity.status(ce.getStatus())
                .body(getErrorMessageForException(request.getRequestURI(),
                        String.format(ExceptionMessages.CHARACTER_EXCEPTION_MESSAGE, ce.getReason())));

    }


    private ErrorMessage getErrorMessageForException(String uri, String message) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(message)
                .path(uri)
                .timestamp(System.currentTimeMillis())
                .build();

        log.error("Exception occurred for URI: [{}] with message detail [{}]", uri, message);
        return errorMessage;
    }

}
