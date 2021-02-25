package uk.co.marvel.character;

import uk.co.marvel.character.data.entities.Character;
import uk.co.marvel.character.data.marvel.MarvelResponse;
import uk.co.marvel.character.data.marvel.MarvelResponseContainer;

import java.util.List;

public class CharacterTestUtils {
    private CharacterTestUtils() {
    }

    public static final Integer CHARACTER_ID = 4444444;

    public static final String CHARACTER_REQUEST_URL = "https://character.site.com/";

    public static Character buildCharacter() {
        var character = new Character();
        character.setId(CHARACTER_ID);
        character.setName("best-man");
        character.setDescription("some-description");

        return character;

    }

    public static MarvelResponseContainer buildMarvelResponseContainer() {
        var limit = 100;
        MarvelResponseContainer marvelResponseContainer = new MarvelResponseContainer();
        MarvelResponse marvelResponse = new MarvelResponse();
        marvelResponse.setLimit(limit);
        marvelResponse.setOffset(0);
        marvelResponse.setCount(limit);
        marvelResponse.setTotal(305);
        marvelResponse.setResults(List.of(buildCharacter(), buildCharacter()));
        marvelResponseContainer.setData(marvelResponse);

        return marvelResponseContainer;
    }
}
