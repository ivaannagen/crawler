package uk.co.monzo.crawler.client;

import lombok.extern.log4j.Log4j2;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uk.co.monzo.crawler.exceptions.CrawlerException;

import java.io.IOException;
import java.util.Optional;

@Component
@Log4j2
public class JsoupClient {

    public Optional<Document> getDocument(String url) {
        try {
            Connection con = Jsoup.connect(url);
            return handle(con);
        }
        catch(IOException | IllegalArgumentException e) {
            log.error(new CrawlerException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("Could not parse Html node with path [%s]", e.getMessage())));
        }
        return Optional.empty();
    }

    private Optional<Document> handle(Connection connection) throws IOException {
        Document node = connection.get();
        int statusCode = connection.response().statusCode();
        if(statusCode == 200) {
            return Optional.of(node);
        }
        else {
            log.error(new CrawlerException(HttpStatus.FAILED_DEPENDENCY, String.format("Could not parse Html node with status code [%s]", statusCode)));
        }
        return Optional.empty();
    }

}