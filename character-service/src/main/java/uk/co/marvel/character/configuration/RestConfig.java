package uk.co.marvel.character.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class RestConfig {

    private final InternalRequestInterceptor internalRequestInterceptor;

    public static final String INTERNAL_REST_TEMPLATE = "internalRestTemplate";

    @Bean(name = INTERNAL_REST_TEMPLATE)
    @Primary
    public RestTemplate internalRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(List.of(internalRequestInterceptor));

        return restTemplate;
    }

}
