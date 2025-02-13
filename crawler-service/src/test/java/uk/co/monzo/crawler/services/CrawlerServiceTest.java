package uk.co.monzo.crawler.services;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.co.monzo.crawler.client.RequestBuilder;
import uk.co.monzo.crawler.client.ResponseHandler;
import uk.co.monzo.crawler.client.RestClient;
import uk.co.monzo.crawler.repository.CrawlerRepository;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class CrawlerServiceTest {

    @Mock
    private RequestBuilder requestBuilder;

    @Mock
    private RestClient restClient;

    @Mock
    private ResponseHandler responseHandler;

    @Mock
    private CrawlerRepository characterRepository;

    @InjectMocks
    private CrawlerService underTest;

//    @Test
//    public void shouldExceptionThrownWhenRestClientHasNullResponse() {
//        when(requestBuilder.buildGetCharactersForIdRequest(CHARACTER_ID)).thenReturn(CHARACTER_REQUEST_URL);
//        when(restClient.get(CHARACTER_REQUEST_URL)).thenReturn(null);
//        var characterException = assertThrows(CrawlerException.class, () -> underTest.fetchUrls(CHARACTER_ID));
//        assertEquals(HttpStatus.NOT_FOUND, characterException.getStatus());
//        assertEquals(ExceptionMessages.CHARACTER_ERROR, characterException.getReason());
//    }
//
//    @Test
//    public void shouldExceptionThrownWhenResponseHasNullData() {
//        var responseContainer = buildMarvelResponseContainer();
//        responseContainer.setData(null);
//        when(requestBuilder.buildGetCharactersForIdRequest(CHARACTER_ID)).thenReturn(CHARACTER_REQUEST_URL);
//        when(restClient.get(CHARACTER_REQUEST_URL)).thenReturn(responseContainer);
//        var characterException = assertThrows(CrawlerException.class, () -> underTest.fetchCharacterById(CHARACTER_ID));
//        assertEquals(HttpStatus.NOT_FOUND, characterException.getStatus());
//        assertEquals(ExceptionMessages.CHARACTER_ERROR, characterException.getReason());
//    }
//
//    @Test
//    public void shouldTestFetchCharacterIdsIsEmptyWhenResponseIsNull() {
//        var limit  = 100;
//        var offset = 0;
//
//        var responseContainer = buildMarvelResponseContainer();
//        var expectedCharacters = List.of(buildCharacter(), (Object) buildCharacter());
//        responseContainer.getData().setResults(expectedCharacters);
//
//        when(requestBuilder.buildGetCharactersRequest(limit, offset)).thenReturn(CHARACTER_REQUEST_URL);
//        when(restClient.get(CHARACTER_REQUEST_URL)).thenReturn(null);
//
//        assertTrue(underTest.fetchCharacters(limit, offset).isEmpty());
//    }
}
