package com.xavelo.crypto.api;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.xavelo.crypto.Coin;
import com.xavelo.crypto.Currency;
import com.xavelo.crypto.Price;
import com.xavelo.crypto.adapter.PriceFetchException;
import com.xavelo.crypto.service.FetchService;
import com.xavelo.crypto.adapter.KafkaAdapter;
import com.xavelo.crypto.service.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.GitProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CryptoFetcherController {

    private static final Logger logger = LogManager.getLogger(CryptoFetcherController.class);

    @Value("${HOSTNAME:unknown}")
    private String podName;

    @Autowired
    private GitProperties gitProperties;

    @Autowired
    private KafkaAdapter kafkaAdapter;

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

    @PostMapping("/produce")
    public ResponseEntity<Message> produce(@RequestBody Message message) {
        kafkaAdapter.produceMessage("test-topic", message);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }



}

