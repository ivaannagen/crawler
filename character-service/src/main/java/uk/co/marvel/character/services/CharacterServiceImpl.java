package uk.co.marvel.character.services;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import uk.co.marvel.character.client.RequestBuilder;
import uk.co.marvel.character.client.ResponseHandler;
import uk.co.marvel.character.client.RestClient;
import uk.co.marvel.character.data.entities.Character;
import uk.co.marvel.character.data.marvel.MarvelResponse;
import uk.co.marvel.character.data.marvel.MarvelResponseContainer;
import uk.co.marvel.character.repository.CharactersRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Configuration
@PropertySource("classpath:application.yml")
@Service
@Log4j2
public class CharacterServiceImpl {

    @Autowired
    private RequestBuilder requestBuilder;

    @Autowired
    private ResponseHandler responseHandler;

    @Autowired
    private RestClient restClient;

    @Autowired
    private CharactersRepository charactersRepository;


    @PostConstruct
    public void fetchCharacterIds() {

        BasicThreadFactory factory = new BasicThreadFactory.Builder()
                .namingPattern("myspringbean-thread-%d").build();

        var executorService = Executors.newSingleThreadExecutor(factory);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    log.info("Attempting to retrieve Characters from Marvel");
                    var characters = fetchCharacters(100, 0);
                    characters.forEach(ca -> charactersRepository.create(ca.getId(), ca));
                    log.info("Successfully retrieved [{}] Characters from Marvel", charactersRepository.getCache().size());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        executorService.shutdown();
    }

    public List<Integer> getCharactersIds(){
        return charactersRepository.getCache().keySet().stream().collect(Collectors.toList());
    }

    public Character fetchCharacterById(Integer characterId) {
        var charactersRequest = requestBuilder.buildGetCharactersForIdRequest(characterId);
        Character character = null;
        MarvelResponseContainer response = restClient.get(charactersRequest);
        if (Objects.nonNull(response)) {
            var data = response.getData();
            if (Objects.nonNull(data)) {
                character = responseHandler.getCharactersResponse(data.getResults()).stream().findAny().orElse(null);
            }

        }
        return character;
    }

    public List<Character> fetchCharacters(Integer limit, Integer offset) {
        List<Character> charactersList = new ArrayList<>();
        List<MarvelResponse> marvelResponses = new ArrayList<>();
        MarvelResponse initialResponse = fetchCharacterIdsResponse(limit, offset);
        marvelResponses.add(initialResponse);
        while (offset < initialResponse.getTotal()) {
            offset += 100;
            marvelResponses.add(fetchCharacterIdsResponse(limit, offset));
        }
        marvelResponses.forEach(mr -> {
            var results = mr.getResults();
            if (CollectionUtils.isNotEmpty(results)) {
                var characters = responseHandler.getCharactersResponse(results);
                charactersList.addAll(characters);
            }
        });

        return charactersList;
    }

    private MarvelResponse fetchCharacterIdsResponse(Integer limit, Integer offset) {
        var charactersRequest = requestBuilder.buildGetCharactersRequest(limit, offset);
        MarvelResponseContainer response = restClient.get(charactersRequest);
        return Objects.nonNull(response) ? response.getData() : null;
    }
}