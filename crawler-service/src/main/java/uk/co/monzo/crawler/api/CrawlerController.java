package uk.co.monzo.crawler.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.co.monzo.crawler.endpoint.CrawlerEndpointValidation;
import uk.co.monzo.crawler.services.CrawlerServiceImpl;

import java.util.Set;


@RestController
@RequestMapping("crawler")
public class CrawlerController {

    @Autowired
    private CrawlerEndpointValidation crawlerEndpointValidation;

    @Autowired
    private CrawlerServiceImpl crawlerService;


    @GetMapping
    public ResponseEntity<Set<String>> getCrawlerUrl(@RequestParam(name = "address") String address, @RequestParam(name = "maxLevel") Integer maxLevel) {
        crawlerEndpointValidation.validateUrl(address);
        crawlerEndpointValidation.validateLevel(maxLevel);
        Set<String> paths = crawlerService.fetchUrls(address, 1, maxLevel);
        return ResponseEntity.ok(paths);
    }

}