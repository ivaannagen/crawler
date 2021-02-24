package uk.co.marvel.character.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@Configuration
@PropertySource("classpath:application.yml")
public class CharacterAppConfig {

    @Value("${marvel.base_url}")
    private String MARVEL_BASE_URL;

    @Value("${marvel.public_key}")
    private String PUBLIC_KEY;

    @Value("${marvel.private_key}")
    private String PRIVATE_KEY;

    @Value("${marvel.timestamp}")
    private Integer TIMESTAMP;

}
