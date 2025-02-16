package uk.co.monzo.crawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "classpath:application.yml")
public class CrawlerApplication {

	public static void main(String[] args) {

		SpringApplication.run(CrawlerApplication.class, args);
	}

}