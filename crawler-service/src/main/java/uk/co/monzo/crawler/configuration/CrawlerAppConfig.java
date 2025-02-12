package uk.co.monzo.crawler.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@Configuration
@PropertySource("classpath:application.yml")
public class CrawlerAppConfig {

    @Value("${monzo.base_url}")
    private String MARVEL_BASE_URL;

    @Value("${monzo.public_key}")
    private String PUBLIC_KEY;

    @Value("${monzo.private_key}")
    private String PRIVATE_KEY;

    @Value("${monzo.timestamp}")
    private Integer TIMESTAMP;

}