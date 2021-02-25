package uk.co.marvel.character.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import uk.co.marvel.character.client.RequestBuilder;
import uk.co.marvel.character.client.ResponseHandler;
import uk.co.marvel.character.client.RestClient;
import uk.co.marvel.character.data.entities.Character;
import uk.co.marvel.character.exceptions.CharacterException;
import uk.co.marvel.character.exceptions.ExceptionMessages;
import uk.co.marvel.character.repository.CharactersRepository;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static uk.co.marvel.character.CharacterTestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class CharacterServiceImplTest {

    @Mock
    private RequestBuilder requestBuilder;

    @Mock
    private RestClient restClient;

    @Mock
    private ResponseHandler responseHandler;

    @Mock
    private CharactersRepository characterRepository;

    @InjectMocks
    private CharacterServiceImpl underTest;

    @Test
    public void shouldTestFetchCharacterByIdReturnsCharacterSuccessfully() {
        var responseContainer = buildMarvelResponseContainer();
        Character expectedCharacter = buildCharacter();
        when(requestBuilder.buildGetCharactersForIdRequest(CHARACTER_ID)).thenReturn(CHARACTER_REQUEST_URL);
        when(restClient.get(CHARACTER_REQUEST_URL)).thenReturn(responseContainer);
        when(responseHandler.getCharactersResponse(any())).thenReturn(Collections.singletonList(expectedCharacter));
        var character = underTest.fetchCharacterById(CHARACTER_ID);
        assertEquals(expectedCharacter, character);
    }

    @Test
    public void shouldExceptionThrownWhenRestClientHasNullResponse() {
        when(requestBuilder.buildGetCharactersForIdRequest(CHARACTER_ID)).thenReturn(CHARACTER_REQUEST_URL);
        when(restClient.get(CHARACTER_REQUEST_URL)).thenReturn(null);
        var characterException = assertThrows(CharacterException.class, () -> underTest.fetchCharacterById(CHARACTER_ID));
        assertEquals(HttpStatus.NOT_FOUND, characterException.getStatus());
        assertEquals(ExceptionMessages.CHARACTER_ERROR, characterException.getReason());
    }

    @Test
    public void shouldExceptionThrownWhenResponseHasNullData() {
        var responseContainer = buildMarvelResponseContainer();
        responseContainer.setData(null);
        when(requestBuilder.buildGetCharactersForIdRequest(CHARACTER_ID)).thenReturn(CHARACTER_REQUEST_URL);
        when(restClient.get(CHARACTER_REQUEST_URL)).thenReturn(responseContainer);
        var characterException = assertThrows(CharacterException.class, () -> underTest.fetchCharacterById(CHARACTER_ID));
        assertEquals(HttpStatus.NOT_FOUND, characterException.getStatus());
        assertEquals(ExceptionMessages.CHARACTER_ERROR, characterException.getReason());
    }
}
