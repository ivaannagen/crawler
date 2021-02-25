package uk.co.marvel.character.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import uk.co.marvel.character.data.marvel.MarvelResponseContainer;
import uk.co.marvel.character.exceptions.ExternalServiceException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static uk.co.marvel.character.CharacterTestUtils.CHARACTER_REQUEST_URL;
import static uk.co.marvel.character.CharacterTestUtils.buildMarvelResponseContainer;

@RunWith(MockitoJUnitRunner.class)
public class RestClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RestClient underTest;

    @Test
    public void shouldTestGestRequestIsInitiatedSuccessfully() {
        org.springframework.http.HttpEntity<String> httpEntity = new org.springframework.http.HttpEntity<>(null, null);
        var expectedMarvelResponseContainer = buildMarvelResponseContainer();
        when(restTemplate.exchange(eq(CHARACTER_REQUEST_URL), eq(HttpMethod.GET), eq(httpEntity),
                eq(MarvelResponseContainer.class))).thenReturn(ResponseEntity.ok(expectedMarvelResponseContainer));

        var marvelResponseContainer = underTest.get(CHARACTER_REQUEST_URL);
        assertEquals(expectedMarvelResponseContainer, marvelResponseContainer);

    }

    @Test
    public void shouldTestExceptionIsThrownWhenResponseEntityIsNotOfStatusOK() {
        org.springframework.http.HttpEntity<String> httpEntity = new org.springframework.http.HttpEntity<>(null, null);
        var expectedMarvelResponseContainer = buildMarvelResponseContainer();
        when(restTemplate.exchange(eq(CHARACTER_REQUEST_URL), eq(HttpMethod.GET), eq(httpEntity),
                eq(MarvelResponseContainer.class))).thenReturn(ResponseEntity.badRequest().body(expectedMarvelResponseContainer));

        var externalServiceException = assertThrows(ExternalServiceException.class, () -> underTest.get(CHARACTER_REQUEST_URL));
        assertEquals(HttpStatus.BAD_REQUEST, externalServiceException.getStatus());
        assertEquals("400 BAD_REQUEST \"Failed to retrieve data from Marvel\"", externalServiceException.getReason());
    }


    @Test
    public void shouldTestExceptionIsThrownWhenRestTemplateThrowsAnException() {
        org.springframework.http.HttpEntity<String> httpEntity = new org.springframework.http.HttpEntity<>(null, null);
        doThrow(ExternalServiceException.class).when(restTemplate).exchange(eq(CHARACTER_REQUEST_URL), eq(HttpMethod.GET), eq(httpEntity),
                eq(MarvelResponseContainer.class));

        var externalServiceException = assertThrows(ExternalServiceException.class, () -> underTest.get(CHARACTER_REQUEST_URL));
        assertEquals(HttpStatus.BAD_REQUEST, externalServiceException.getStatus());
    }

}
