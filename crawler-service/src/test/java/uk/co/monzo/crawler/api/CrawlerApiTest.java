package uk.co.monzo.crawler.api;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.monzo.crawler.exceptions.CrawlerException;
import uk.co.monzo.crawler.services.CrawlerService;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static uk.co.monzo.crawler.CrawlerTestUtils.BASE_URL;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CrawlerApiTest {

    @MockBean
    private CrawlerService crawlerService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CrawlerApi apiUnderTest;

    @Test
    void refreshCrawler() {
        ResponseEntity<Map<String, Set<String>>> response = apiUnderTest.refreshCrawler();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Map<String, Set<String>> visited = response.getBody();
        assertThat(visited).isEmpty();
    }

    @Test
    void getVisited() {
        //crawler level 0
        String currentAccount = "current-account/";
        String legal = "legal/";

        //crawler level 1
        String goals = "goals/";
        String interest = "interest/";

        String general = "general/";
        String policy = "policy/";

        Map<String, Set<String>> visited = Map.of(
                currentAccount, Set.of(goals, interest),
                legal, Set.of(general, policy)
        );
        when(crawlerService.fetchUrls(BASE_URL, 1)).thenReturn(visited);

        ResponseEntity<Map<String, Set<String>>> response = apiUnderTest.getVisited(BASE_URL, 1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(visited);
    }

    @Test
    void getVisitedInvalidUrl() {
        assertThatThrownBy(() -> apiUnderTest.getVisited(StringUtils.EMPTY, 1))
        .isInstanceOf(CrawlerException.class)
        .hasMessageContaining("Url to crawl is invalid")
        .hasMessageContaining("400 BAD_REQUEST ");
    }

}