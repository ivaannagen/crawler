package uk.co.marvel.character.configuration;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import uk.co.marvel.character.client.ResponseHandler;
import uk.co.marvel.character.exceptions.ExternalServiceException;
import uk.co.marvel.character.utils.RequestUtils;

import java.io.IOException;

import static uk.co.marvel.character.exceptions.ExceptionMessages.MARVEL_ERROR;

@Component
@Log4j2
public class InternalRequestInterceptor implements ClientHttpRequestInterceptor {

    @Autowired
    private CharacterAppConfig characterAppConfig;

    @Autowired
    private ResponseHandler responseHandler;


    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte [] body, ClientHttpRequestExecution execution) {
        try {
        request.getHeaders().set(RequestUtils.CONTENT_TYPE_HEADER, RequestUtils.CONTENT_TYPE_VALUE);
        return execution.execute(request, body);
        }
        catch(IOException ioe) {
            throw new ExternalServiceException(HttpStatus.FAILED_DEPENDENCY, MARVEL_ERROR);
        }
    }

}
