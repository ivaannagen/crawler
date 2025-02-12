package uk.co.monzo.crawler.endpoint;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.co.monzo.crawler.services.CrawlerServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class CrawlerEndpointValidationTest {

    @Mock
    private CrawlerServiceImpl characterService;

    @InjectMocks
    private CrawlerEndpointValidation underTest;

//
//    @Test
//    public void shouldTestCharacterIsRetrievedByIdSuccessfully() {
//        var character = buildCharacter();
//        when(characterService.fetchCharacterById(CHARACTER_ID)).thenReturn(character);
//        var characterResponse = underTest.getCharacterById(CHARACTER_ID);
//        assertEquals(HttpStatus.OK, characterResponse.getStatusCode());
//        assertEquals(character, characterResponse.getBody());
//    }
//
//    @Test
//    public void shouldTestExceptionIsThrownWhenCharacterIdIsOfInvalidFormat() {
//        var characterException = assertThrows(CrawlerException.class, () -> underTest.getCharacterById(1));
//        assertEquals(HttpStatus.BAD_REQUEST, characterException.getStatus());
//        assertEquals("Character ID must be of size [7]", characterException.getReason());
//    }

}
