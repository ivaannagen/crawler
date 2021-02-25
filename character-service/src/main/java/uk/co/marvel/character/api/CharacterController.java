package uk.co.marvel.character.api;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import uk.co.marvel.character.data.entities.Character;
import uk.co.marvel.character.endpoint.CharacterEndpoint;

import java.util.List;


@RestController
@RequestMapping("characters")
public class CharacterController {

    @Autowired
    private CharacterEndpoint characterEndpoint;

    @GetMapping
    public List<Integer> getCharacterIds() {

        return characterEndpoint.getCharacterIds();
    }


    @GetMapping("{characterId}")
    public ResponseEntity<Character> getCharacterById(@PathVariable Integer characterId) {
        return characterEndpoint.getCharacterById(characterId);
    }

}
