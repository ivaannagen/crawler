package uk.co.marvel.character.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.co.marvel.character.configuration.CharacterAppConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RequestBuilderTest {

    @Mock
    private CharacterAppConfig characterAppConfig;

    @InjectMocks
    private RequestBuilder underTest;

    private final String MARVEL_BASE_URL = "https://gateway.marvel.com/";

    @Test
    public void shouldTestGetCharactersRequestIsBuiltCorrectly() {
        when(characterAppConfig.getMARVEL_BASE_URL()).thenReturn(MARVEL_BASE_URL);
        when(characterAppConfig.getTIMESTAMP()).thenReturn(1);
        when(characterAppConfig.getPRIVATE_KEY()).thenReturn("private-key");
        when(characterAppConfig.getPUBLIC_KEY()).thenReturn("public-key");
        var request = underTest.buildGetCharactersRequest(100, 0);

        assertEquals("https://gateway.marvel.com/v1/public/characters?" +
                "limit=100&offset=0&ts=1&apikey=public-key&hash=852d790bf3313eeecf72b436795fa4d9", request);

    }

    @Test
    public void shouldTestGetCharactersForIdRequestIsBuiltCorrectly() {
        when(characterAppConfig.getMARVEL_BASE_URL()).thenReturn(MARVEL_BASE_URL);
        when(characterAppConfig.getTIMESTAMP()).thenReturn(1);
        when(characterAppConfig.getPRIVATE_KEY()).thenReturn("private-key");
        when(characterAppConfig.getPUBLIC_KEY()).thenReturn("public-key");
        var request = underTest.buildGetCharactersForIdRequest(1234567);

        assertEquals("https://gateway.marvel.com//v1/public/characters/1234567?" +
                "&ts=1&apikey=public-key&hash=852d790bf3313eeecf72b436795fa4d9", request);

    }


}
