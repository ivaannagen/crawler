package uk.co.monzo.crawler.repository;

import org.springframework.stereotype.Component;
import uk.co.monzo.crawler.data.entities.Character;

@Component
public class CrawlerRepository extends AbstractCRUD<Integer, Character> {

    @Override
    public Character create(Integer id, Character character) {
        return super.create(id, character);
    }
}
