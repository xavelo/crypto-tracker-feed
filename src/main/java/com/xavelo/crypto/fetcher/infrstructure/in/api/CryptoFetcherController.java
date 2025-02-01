package com.xavelo.crypto.fetcher.infrstructure.in.api;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xavelo.crypto.fetcher.application.PriceFetchException;
import com.xavelo.crypto.fetcher.domain.model.Coin;
import com.xavelo.crypto.fetcher.domain.model.Currency;
import com.xavelo.crypto.fetcher.domain.model.Price;
import com.xavelo.crypto.fetcher.domain.repository.FetchService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.GitProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CryptoFetcherController {

    private static final Logger logger = LoggerFactory.getLogger(CryptoFetcherController.class);

    @Value("${HOSTNAME:unknown}")
    private String podName;

    @Autowired
    private GitProperties gitProperties;

    @Autowired
    private FetchService fetchService;

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        String commitId = gitProperties.getCommitId();
        LocalDateTime dateTime = LocalDateTime.ofInstant(gitProperties.getCommitTime(), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String commitTime = dateTime.format(formatter);
        logger.info("pong from pod {} - commitId {} - commitTime {}", commitId, commitTime, podName);
        return ResponseEntity.ok("pong from pod " + podName);
    }

    @GetMapping("/fetch/{coin}/{currency}")
    public ResponseEntity<Price> fetch(@PathVariable String coin, @PathVariable String currency) {
        logger.info("-> fetch coin {} in {} currency", coin, currency);
        try {
            Price price = fetchService.fetchPrice(Coin.valueOf(coin), Currency.valueOf(currency));
            return ResponseEntity.ok(price);
        } catch (PriceFetchException pfe) {
            logger.error("Failed to fetch price for {} in {}", coin, currency, pfe);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/publish/{coin}/{currency}")
    public ResponseEntity<Price> fetchAndPublish(@PathVariable String coin, @PathVariable String currency) {
        logger.info("-> fetch and publish coin {} in {} currency", coin, currency);
        try {
            Price price = fetchService.fetchAndPublishPrice(Coin.valueOf(coin), Currency.valueOf(currency));
            return ResponseEntity.ok(price);
        } catch (PriceFetchException pfe) {
            logger.error("Failed to fetch price for {} in {}", coin, currency, pfe);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (JsonProcessingException jpe) {
            logger.error("Failed to process price format for {} in {}", coin, currency, jpe);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

