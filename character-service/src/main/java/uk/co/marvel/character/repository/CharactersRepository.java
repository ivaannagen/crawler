package uk.co.marvel.character.repository;

import org.springframework.stereotype.Component;
import uk.co.marvel.character.data.entities.Character;

@Component
public class CharactersRepository extends AbstractCRUD<Integer, Character> {

    @Override
    public Character create(Integer id, Character character) {
        return super.create(id, character);
    }
}
