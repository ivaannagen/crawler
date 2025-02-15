package uk.co.monzo.crawler.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.co.monzo.crawler.services.CrawlerService;
import uk.co.monzo.crawler.validation.CrawlerEndpointValidation;

import java.util.Map;
import java.util.Set;


@RestController
@RequestMapping("crawler")
public class CrawlerController {

    private final CrawlerEndpointValidation crawlerEndpointValidation;

    private final CrawlerService crawlerService;

    @Autowired
    public CrawlerController(CrawlerEndpointValidation crawlerEndpointValidation, CrawlerService crawlerService) {
        this.crawlerEndpointValidation = crawlerEndpointValidation;
        this.crawlerService = crawlerService;
    }


    @GetMapping
    public ResponseEntity<Map<String, Set<String>>> getCrawlerUrl(@RequestParam(name = "address") String address, @RequestParam(name = "maxLevel") Integer maxLevel) {
        crawlerEndpointValidation.validateUrl(address);
        crawlerEndpointValidation.validateMaxLevel(maxLevel);
        Map<String, Set<String>> paths = crawlerService.fetchUrls(address, maxLevel);
        return ResponseEntity.ok(paths);
    }

}