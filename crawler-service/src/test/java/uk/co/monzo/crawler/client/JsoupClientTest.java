package uk.co.monzo.crawler.client;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class JsoupClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private JsoupClient underTest;
//
//    @Test
//    public void shouldTestGestRequestIsInitiatedSuccessfully() {
//        org.springframework.http.HttpEntity<String> httpEntity = new org.springframework.http.HttpEntity<>(null, null);
//        var expectedMarvelResponseContainer = buildMarvelResponseContainer();
//        when(restTemplate.exchange(eq(CHARACTER_REQUEST_URL), eq(HttpMethod.GET), eq(httpEntity),
//                eq(MarvelResponseContainer.class))).thenReturn(ResponseEntity.ok(expectedMarvelResponseContainer));
//
//        var marvelResponseContainer = underTest.get(CHARACTER_REQUEST_URL);
//        assertEquals(expectedMarvelResponseContainer, marvelResponseContainer);
//
//    }

}
