package uk.co.monzo.crawler.services;

import lombok.extern.log4j.Log4j2;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.monzo.crawler.client.JsoupClient;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Log4j2
public class CrawlerWorker implements Runnable {

    private final JsoupClient jsoupClient;

    @Autowired
    public CrawlerWorker(JsoupClient jsoupClient) {
        this.jsoupClient = jsoupClient;
    }

    @Override
    public void run() {

    }

    public Set<String> fetchUrls(String url) {
        Optional<Document> nodeOpt = jsoupClient.getDocument(url);

        if (nodeOpt.isPresent()) {
            Document node = nodeOpt.get();

            return node.select("a[href]").stream()
                    .map(nextLink -> nextLink.attr("abs:href"))
                    .collect(Collectors.toSet());
        }
        return Set.of();
    }

}