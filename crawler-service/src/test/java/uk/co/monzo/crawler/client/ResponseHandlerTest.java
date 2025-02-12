package uk.co.monzo.crawler.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.co.monzo.crawler.data.entities.Character;
import uk.co.monzo.crawler.utils.ObjectUnwrapper;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResponseHandlerTest {

    @Mock
    private ObjectUnwrapper<Character> objectUnwrapper;

    @InjectMocks
    private ResponseHandler underTest;

    @Test
    public void shouldTestEmptyCollectionIsReturnedWhenUnwrappedResultsAreEmpty() {
        var result = new Object();
        when(objectUnwrapper.unwrapCollection(result, Character.class)).thenReturn(Collections.emptyList());
        assertTrue(underTest.getCharactersResponse(result).isEmpty());

    }

    @Test
    public void shouldTestCollectionOfCharactersIsReturnedSuccessfully() {
        var result = new Object();
        var expectedCharacters = List.of(new Character(), new Character());
        when(objectUnwrapper.unwrapCollection(result, Character.class)).thenReturn(expectedCharacters);
        var characters = underTest.getCharactersResponse(result);
        assertEquals(2, characters.size());
        assertEquals(expectedCharacters, characters);
    }
}
