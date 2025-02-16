package uk.co.monzo.crawler.services;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.co.monzo.crawler.client.JsoupClient;
import uk.co.monzo.crawler.repository.CrawlerRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static uk.co.monzo.crawler.CrawlerTestUtils.BASE_URL;

@RunWith(MockitoJUnitRunner.class)
public class CrawlerServiceTest {


    @Mock
    private JsoupClient jsoupClient;

    @Mock
    private CrawlerRepository characterRepository;

    @InjectMocks
    private CrawlerService underTest;

    @Test
    public void shouldSuccessfullyFetchNoUrlsToVisit() {
        when(jsoupClient.getDocument(BASE_URL)).thenReturn(Optional.empty());
        Set<String> urlsToVisit = underTest.fetchUrlsToVisit(BASE_URL);
        assertThat(urlsToVisit).isEmpty();
    }

    @Test
    public void shouldSuccessfullyFetchUrlsToVisit() {
        Document document = mock(Document.class);
        Elements elements = new Elements(List.of(new Element(BASE_URL + "legal/"), new Element(BASE_URL + "savings/")));

        List<String> stringStream = elements.stream().map(element -> element.attr("abs:href")).collect(Collectors.toList());

        when(document.select("a[href]")).thenReturn(elements);
        when(jsoupClient.getDocument(BASE_URL)).thenReturn(Optional.of(document));

        Set<String> urlsToVisit = underTest.fetchUrlsToVisit(BASE_URL);

        assertThat(urlsToVisit).isNotEmpty();

//        assertThat(urlsToVisit).satisfiesAnyOf(
//                url1 -> assertThat(url1).isEqualTo(BASE_URL + "legal/"),
//                url2 -> assertThat(url2).isEqualTo(BASE_URL + "savings/")
//        );
    }


}