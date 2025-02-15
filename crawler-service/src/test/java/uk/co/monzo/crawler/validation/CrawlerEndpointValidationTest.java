package uk.co.monzo.crawler.validation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.co.monzo.crawler.configuration.CrawlerAppConfig;
import uk.co.monzo.crawler.exceptions.CrawlerException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CrawlerEndpointValidationTest {

    @Mock
    private CrawlerAppConfig crawlerAppConfig;

    @InjectMocks
    private CrawlerEndpointValidation underTest;

    @Before
    public void setup() {
        when(crawlerAppConfig.getMAX_LEVEL()).thenReturn(5);
    }

    @Test
    public void shouldPassValidationForMaxLevel() {
        assertDoesNotThrow(() -> underTest.validateMaxLevel(1));
    }

    @Test
    public void shouldThrowWhenMaxLevelIsNegative() {
        assertThatThrownBy(() -> underTest.validateMaxLevel(-1))
        .isInstanceOf(CrawlerException.class)
        .hasMessageContaining("Level to crawl is invalid");
    }

    @Test
    public void shouldThrowWhenMaxLevelIsZero() {
        assertThatThrownBy(() -> underTest.validateMaxLevel(0))
                .isInstanceOf(CrawlerException.class)
                .hasMessageContaining("Level to crawl is invalid");
    }

    @Test
    public void shouldThrowWhenMaxLevelIsGreaterThanThreshold() {
        assertThatThrownBy(() -> underTest.validateMaxLevel(6))
                .isInstanceOf(CrawlerException.class)
                .hasMessageContaining("Level to crawl is invalid");
    }

}