package uk.co.monzo.crawler.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uk.co.monzo.crawler.exceptions.CrawlerException;

import java.util.List;

import static uk.co.monzo.crawler.exceptions.ExceptionMessages.UNMARSHAL_ERROR;

@Component
public class ObjectUnwrapper<T> {

    @Autowired
    private ObjectMapper objectMapper;

    public List<T> unwrapCollection(Object obj, Class<T> clazz) {
        try {
            JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
            return objectMapper.convertValue(obj, type);
        }

        catch (IllegalArgumentException iae) {
            iae.printStackTrace();
            throw new CrawlerException(HttpStatus.INTERNAL_SERVER_ERROR, UNMARSHAL_ERROR);
        }
    }
}
