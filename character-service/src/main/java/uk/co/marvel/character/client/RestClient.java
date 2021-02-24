package uk.co.marvel.character.client;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uk.co.marvel.character.configuration.RestConfig;
import uk.co.marvel.character.data.marvel.MarvelResponseContainer;
import uk.co.marvel.character.exceptions.ExternalServiceException;

import static uk.co.marvel.character.exceptions.ExceptionMessages.MARVEL_ERROR;

@Component
@Log4j2
public class RestClient {

    @Autowired
    @Qualifier(value = RestConfig.INTERNAL_REST_TEMPLATE)
    private RestTemplate restTemplate;


    public MarvelResponseContainer get(String url) {
        org.springframework.http.HttpEntity<String> httpEntity = new org.springframework.http.HttpEntity<>(null, null);
        try {
            log.info("Attempting to retrieve data from Marvel");
            var responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, MarvelResponseContainer.class);
            HttpStatus httpStatus = responseEntity.getStatusCode();
            if (HttpStatus.OK.equals(httpStatus)) {
                log.info("Successful Response from Marvel");
                return responseEntity.getBody();
            } else {
                throw new ExternalServiceException(httpStatus, MARVEL_ERROR);
            }

        } catch (Exception e) {
            throw new ExternalServiceException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
