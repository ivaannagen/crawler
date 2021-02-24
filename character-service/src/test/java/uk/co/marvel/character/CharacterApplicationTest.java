package uk.co.marvel.character;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CharacterApplicationTest {

	@Autowired
	TestRestTemplate restTemplate;

	@Test
	void contextLoads() {
	}

	@Test
	void getStagedEvents() {
		ResponseEntity response = restTemplate.getForEntity("/events/football", String.class);

		assertEquals(200, response.getStatusCodeValue());
		assertTrue(response.hasBody());
		assertEquals("Show all events in daily cache. ", response.getBody());
	}

}
