package com.xavelo.crypto.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaService.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void produceMessage(String topic, Message message) {
        LOGGER.info("-> topic '{}' --- message '{}'", topic, message);
        kafkaTemplate.send(topic, message.getValue());
    }

    public void sendAsynchMessage(String message) {
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("pi-topic", message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                LOGGER.error("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                LOGGER.error("Unable to send message=[" + message + "] due to : " + ex.getMessage());
            }
        });
    }

}

