package uk.co.allica.customer.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@Configuration
@PropertySource("classpath:application.yml")
public class CustomerAppConfig {

    @Value("${allica.customer.max-level}")
    private int MAX_LEVEL;

}