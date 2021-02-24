package uk.co.marvel.character.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uk.co.marvel.character.exceptions.CharacterException;

import java.util.List;

import static uk.co.marvel.character.exceptions.ExceptionMessages.UNMARSHAL_ERROR;

@Component
public class ObjectUnwrapper<T> {

    @Autowired
    private ObjectMapper objectMapper;

    public T unwrapObject(Object obj, Class<T> clazz) {
        try {
            return objectMapper.convertValue(obj, clazz);
        }

        catch (IllegalArgumentException iae) {
            iae.printStackTrace();
            throw new CharacterException(HttpStatus.INTERNAL_SERVER_ERROR, UNMARSHAL_ERROR);
        }
    }

    public List<T> unwrapCollection(Object obj, Class<T> clazz) {
        try {
            JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
            return objectMapper.convertValue(obj, type);
        }

        catch (IllegalArgumentException iae) {
            iae.printStackTrace();
            throw new CharacterException(HttpStatus.INTERNAL_SERVER_ERROR, UNMARSHAL_ERROR);
        }
    }
}
