package uk.co.monzo.crawler.client;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.monzo.crawler.CrawlerTestUtils.BASE_URL;

@RunWith(MockitoJUnitRunner.class)
public class JsoupClientTest {

    @InjectMocks
    private JsoupClient underTest;

    @Test
    public void shouldGetDocumentSuccessfully(){
        Optional<Document> document = underTest.getDocument(BASE_URL);
        assertThat(document).hasValueSatisfying(
                doc -> {
                    Connection.Response response = doc.connection().response();
                    assertThat(response.statusCode()).isEqualTo(200);
                }
        );
    }

    @Test
    public void shouldFailToGetDocument(){
        Optional<Document> document = underTest.getDocument("ht:invalid-url");
        assertThat(document).isEmpty();
    }

}