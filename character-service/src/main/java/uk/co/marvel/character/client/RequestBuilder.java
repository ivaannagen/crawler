package uk.co.marvel.character.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.marvel.character.configuration.CharacterAppConfig;
import uk.co.marvel.character.utils.HashUtils;


@Component
public class RequestBuilder {

    @Autowired
    private CharacterAppConfig characterAppConfig;


    public String buildGetCharactersRequest(Integer limit, Integer offset) {
        var getCharactersEndpoint = "v1/public/characters?";
        return characterAppConfig.getMARVEL_BASE_URL() + getCharactersEndpoint + getLimitAndOffset(limit, offset) + getSecurityCredentials();
    }

    public String buildGetCharactersForIdRequest(Integer characterId) {
        var getCharactersEndpoint = "/v1/public/characters/" + characterId + "?";
        return characterAppConfig.getMARVEL_BASE_URL() + getCharactersEndpoint + getSecurityCredentials();
    }

    private String getLimitAndOffset(Integer limit, Integer offset) {
        return "limit=" + limit + "&offset=" + offset;
    }

    private String getSecurityCredentials() {
        return "&ts=" + characterAppConfig.getTIMESTAMP() +
                "&apikey=" + characterAppConfig.getPUBLIC_KEY() +
                "&hash=" + HashUtils.getHashValue((
                characterAppConfig.getTIMESTAMP() +
                        characterAppConfig.getPRIVATE_KEY() +
                        characterAppConfig.getPUBLIC_KEY()));
    }

}
