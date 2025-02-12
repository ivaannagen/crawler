package uk.co.monzo.crawler.client;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.monzo.crawler.data.entities.Character;
import uk.co.monzo.crawler.utils.ObjectUnwrapper;

import java.util.Collections;
import java.util.List;

@Component
@Log4j2
public class ResponseHandler {


    @Autowired
    private ObjectUnwrapper<Character> objectUnwrapper;

    public List<Character> getCharactersResponse(Object result) {
        var characterResults = objectUnwrapper.unwrapCollection(result, Character.class);
        if (CollectionUtils.isEmpty(characterResults)) {
            return Collections.emptyList();
        }
        return characterResults;
    }

}
