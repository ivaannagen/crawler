package uk.co.marvel.character.scheduling;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.marvel.character.repository.CharactersRepository;
import uk.co.marvel.character.services.CharacterServiceImpl;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;

@Service
@Log4j2
public class FetchCharacterIdsJob implements Job {

    @Autowired
    private CharactersRepository charactersRepository;

    @Autowired
    private CharacterServiceImpl characterService;

    @Override
    @PostConstruct
    public void run() {
        BasicThreadFactory factory = new BasicThreadFactory.Builder()
                .namingPattern("myspringbean-thread-%d").build();

        var executorService = Executors.newSingleThreadExecutor(factory);
        executorService.execute(() -> {
            try {
                log.info("Attempting to retrieve Characters from Marvel");
                var characters = characterService.fetchCharacters(100, 0);
                characters.forEach(ca -> charactersRepository.create(ca.getId(), ca));
                log.info("Successfully retrieved [{}] Characters from Marvel", charactersRepository.getCache().size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();

    }
}
