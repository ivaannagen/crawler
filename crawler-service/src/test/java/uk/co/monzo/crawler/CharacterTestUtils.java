package uk.co.monzo.crawler;

import uk.co.monzo.crawler.data.entities.Character;
import uk.co.monzo.crawler.data.marvel.MarvelResponse;
import uk.co.monzo.crawler.data.marvel.MarvelResponseContainer;

import java.util.List;

public class CharacterTestUtils {
    private CharacterTestUtils() {
    }

    public static final String BASE_URL = "http://monozo.com/";

    public static final String CHARACTER_REQUEST_URL = "https://crawler.site.com/";

    public static Character buildCharacter() {
        var character = new Character();
//        character.setId(CHARACTER_ID);
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
