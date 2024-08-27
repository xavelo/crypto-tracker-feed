package com.xavelo.crypto.api;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.xavelo.crypto.service.KafkaService;
import com.xavelo.crypto.service.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.GitProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CryptoFetcherController {

    private static final Logger logger = LogManager.getLogger(CryptoFetcherController.class);

    @Value("${HOSTNAME:unknown}")
    private String podName;

    @Autowired
    private GitProperties gitProperties;

    @Autowired
    private KafkaService kafkaService;

    @GetMapping("/hello")
    public ResponseEntity<Hello> hello() {
        String commitId = gitProperties.getCommitId();
        LocalDateTime dateTime = LocalDateTime.ofInstant(gitProperties.getCommitTime(), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String commitTime = dateTime.format(formatter);
        logger.info("hello from pod {} - commitId {} - commitTime {}", commitId, commitTime, podName);
        return ResponseEntity.ok(new Hello("hello from pod " + podName, commitId + " - " + commitTime));
    }

    @PostMapping("/produce")
    public ResponseEntity<Message> produce(@RequestBody Message message) {
        kafkaService.produceMessage("test-topic", message);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }



}

