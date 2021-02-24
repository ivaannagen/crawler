package uk.co.marvel.character.endpoint;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import uk.co.marvel.character.data.entities.Character;
import uk.co.marvel.character.exceptions.CharacterException;
import uk.co.marvel.character.services.CharacterServiceImpl;

import java.util.List;

@Service
@Log4j2
public class CharacterEndpoint {

    @Autowired
    private CharacterServiceImpl characterService;

    @GetMapping
    public List<Integer> getCharacterIds() {

        var characterIds = characterService.getCharactersIds();

        if(CollectionUtils.isEmpty(characterIds)) {
            log.warn("Request acknowledged - Still loading marvel Characters...");
            throw new CharacterException(HttpStatus.ACCEPTED, "Please come back soon: Marvel Characters are still loading...");
        }

        return characterIds;
    }


    @GetMapping("{characterId}")
    public ResponseEntity<Character> getCharacterById(@PathVariable Integer characterId) {
        var characterIdSize = 7;
        if (characterId.toString().length() != characterIdSize) {
            throw new CharacterException(HttpStatus.BAD_REQUEST, String.format("Character ID must be of size [%d]", characterIdSize));
        }
        return ResponseEntity.ok(characterService.fetchCharacterById(characterId));
    }
}
