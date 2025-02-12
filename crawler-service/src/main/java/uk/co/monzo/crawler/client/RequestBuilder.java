package uk.co.monzo.crawler.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.monzo.crawler.configuration.CrawlerAppConfig;
import uk.co.monzo.crawler.utils.HashUtils;


@Component
public class RequestBuilder {

    @Autowired
    private CrawlerAppConfig crawlerAppConfig;


    public String buildGetCharactersRequest(Integer limit, Integer offset) {
        var getCharactersEndpoint = "v1/public/characters?";
        return crawlerAppConfig.getMARVEL_BASE_URL() + getCharactersEndpoint + getLimitAndOffset(limit, offset) + getSecurityCredentials();
    }

    public String buildGetCharactersForIdRequest(Integer characterId) {
        var getCharactersEndpoint = "/v1/public/characters/" + characterId + "?";
        return crawlerAppConfig.getMARVEL_BASE_URL() + getCharactersEndpoint + getSecurityCredentials();
    }

    private String getLimitAndOffset(Integer limit, Integer offset) {
        return "limit=" + limit + "&offset=" + offset;
    }

    private String getSecurityCredentials() {
        return "&ts=" + crawlerAppConfig.getTIMESTAMP() +
                "&apikey=" + crawlerAppConfig.getPUBLIC_KEY() +
                "&hash=" + HashUtils.getHashValue((
                crawlerAppConfig.getTIMESTAMP() +
                        crawlerAppConfig.getPRIVATE_KEY() +
                        crawlerAppConfig.getPUBLIC_KEY()));
    }

}
