package uk.co.monzo.crawler.services;

import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.co.monzo.crawler.client.JsoupClient;
import uk.co.monzo.crawler.repository.CrawlerRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static uk.co.monzo.crawler.CrawlerTestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class CrawlerServiceTest {


    @Mock
    private JsoupClient jsoupClient;

    @Mock
    private CrawlerRepository crawlerRepository;

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
        String currentAccountRelative = "current-account/";
        String legalRelative = "legal/";
        setUpDocument(BASE_URL, List.of(currentAccountRelative, legalRelative));


        Set<String> urlsToVisit = underTest.fetchUrlsToVisit(BASE_URL);

        assertThat(urlsToVisit).containsExactlyInAnyOrder(
                BASE_URL + currentAccountRelative,
                BASE_URL + legalRelative
        );
    }

    @Test
    public void shouldCrawlNoUrls() {
        when(jsoupClient.getDocument(BASE_URL)).thenReturn(Optional.empty());
        Map<String, Set<String>> visited = underTest.fetchUrls(BASE_URL, 1);
        assertThat(visited).isEmpty();
    }

    @Test
    public void shouldSuccessfullyCrawlUrls() {
        //crawl level 0
        String currentAccountRelative = "current-account/";
        setUpDocument(BASE_URL, List.of(currentAccountRelative));

        //crawl level 1
        String goals  = "goals/";
        String interest  = "interest/";
        setUpDocument(BASE_URL + currentAccountRelative, List.of(goals, interest));

        Map<String, Set<String>> visited = underTest.fetchUrls(BASE_URL, 1);
        assertThat(visited).containsExactlyInAnyOrderEntriesOf(
                Map.of(
                        BASE_URL, Set.of(
                                BASE_URL + currentAccountRelative
                        ),
                        BASE_URL + currentAccountRelative, Set.of(
                                BASE_URL + currentAccountRelative + goals,
                                BASE_URL + currentAccountRelative + interest
                        )
                )
        );
    }

    @Test
    public void shouldSuccessfullyCrawlUrlsWithoutDuplicates() {
        //crawl level 0
        String currentAccountRelative = "current-account/";
        setUpDocument(BASE_URL, List.of(currentAccountRelative));

        //crawl level 1
        String goals  = "goals/";
        String goalsDuplicate  = "goals/";
        String interest  = "interest/";
        setUpDocument(BASE_URL + currentAccountRelative, List.of(goals, goalsDuplicate, interest));

        Map<String, Set<String>> visited = underTest.fetchUrls(BASE_URL, 1);
        assertThat(visited).containsExactlyInAnyOrderEntriesOf(
                Map.of(
                        BASE_URL, Set.of(
                                BASE_URL + currentAccountRelative
                        ),
                        BASE_URL + currentAccountRelative, Set.of(
                                BASE_URL + currentAccountRelative + goals,
                                BASE_URL + currentAccountRelative + interest
                        )
                )
        );
    }

      private void setUpDocument(String url, List<String> additionalPaths) {
        Document document = mock(Document.class);

        Elements elements = new Elements(additionalPaths.size());
        for (String path : additionalPaths) {
            Attributes attributes = new Attributes();
            attributes.add(HREF, path);
            elements.add(new Element(Tag.valueOf(TAG), url, attributes));
        }


        when(document.select(eq("a[href]"))).thenReturn(elements);

        when(jsoupClient.getDocument(url)).thenReturn(Optional.of(document));
    }

}