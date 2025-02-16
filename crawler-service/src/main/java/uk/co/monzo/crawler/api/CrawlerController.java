package uk.co.monzo.crawler.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.co.monzo.crawler.services.CrawlerService;
import uk.co.monzo.crawler.validation.CrawlerEndpointValidation;

import java.util.Map;
import java.util.Set;


@RestController
@RequestMapping("crawler")
public class CrawlerController implements CrawlerApi {

    private final CrawlerEndpointValidation crawlerEndpointValidation;

    private final CrawlerService crawlerService;

    @Autowired
    public CrawlerController(CrawlerEndpointValidation crawlerEndpointValidation, CrawlerService crawlerService) {
        this.crawlerEndpointValidation = crawlerEndpointValidation;
        this.crawlerService = crawlerService;
    }


    @Override
    @GetMapping
    public ResponseEntity<Map<String, Set<String>>> getVisited(@RequestParam(name = "address") String address, @RequestParam(defaultValue = "1", name = "maxLevel") Integer maxLevel) {
        crawlerEndpointValidation.validateUrl(address);
        crawlerEndpointValidation.validateMaxLevel(maxLevel);
        Map<String, Set<String>> paths = crawlerService.fetchUrls(address, maxLevel);
        return ResponseEntity.ok(paths);
    }

    @Override
    @PutMapping("refresh")
    public ResponseEntity<Map<String, Set<String>>> refreshCrawler() {
        return ResponseEntity.ok(crawlerService.refreshCache());
    }

}