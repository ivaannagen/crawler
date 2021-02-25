package uk.co.marvel.character.endpoint;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import uk.co.marvel.character.exceptions.CharacterException;
import uk.co.marvel.character.services.CharacterServiceImpl;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static uk.co.marvel.character.CharacterTestUtils.CHARACTER_ID;
import static uk.co.marvel.character.CharacterTestUtils.buildCharacter;

@RunWith(MockitoJUnitRunner.class)
public class CharacterEndpointTest {

    @Mock
    private CharacterServiceImpl characterService;

    @InjectMocks
    private CharacterEndpoint underTest;

    @Test
    public void shouldTestCharacterIdsAreRetrievedSuccessfully() {
        var expectedCharacterIds = List.of(1111111, 2222222, 3333333);
        when(characterService.getCharactersIds()).thenReturn(expectedCharacterIds);
        var characterIds = underTest.getCharacterIds();
        assertEquals(expectedCharacterIds, characterIds);
    }

    @Test
    public void shouldTestExceptionIsThrownWhenCharacterIdsIsEmpty() {
        when(characterService.getCharactersIds()).thenReturn(Collections.emptyList());
        var characterException = assertThrows(CharacterException.class, () -> underTest.getCharacterIds());
        assertEquals(HttpStatus.ACCEPTED, characterException.getStatus());
        assertEquals("Please come back soon: Marvel Characters are still loading...", characterException.getReason());
    }

    @Test
    public void shouldTestCharacterIsRetrievedByIdSuccessfully() {
        var character = buildCharacter();
        when(characterService.fetchCharacterById(CHARACTER_ID)).thenReturn(character);
        var characterResponse = underTest.getCharacterById(CHARACTER_ID);
        assertEquals(HttpStatus.OK, characterResponse.getStatusCode());
        assertEquals(character, characterResponse.getBody());
    }

    @Test
    public void shouldTestExceptionIsThrownWhenCharacterIdIsOfInvalidFormat() {
        var characterException = assertThrows(CharacterException.class, () -> underTest.getCharacterById(1));
        assertEquals(HttpStatus.BAD_REQUEST, characterException.getStatus());
        assertEquals("Character ID must be of size [7]", characterException.getReason());
    }

}
