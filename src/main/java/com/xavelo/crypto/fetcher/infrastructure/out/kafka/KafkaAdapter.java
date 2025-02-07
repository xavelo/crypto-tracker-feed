package com.xavelo.crypto.fetcher.infrastructure.out.kafka;

import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Component
public class KafkaAdapter {

    private static final Logger logger = LoggerFactory.getLogger(KafkaAdapter.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void publishMessage(String topic, String key, String message) {
        logger.debug("-> topic '{}' --- key '{}' -  message '{}'", topic, key, message);
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, message);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(record);
        future.whenComplete((result, e) -> {
            if (e == null) {
                logger.debug("Message successfully sent to '{}' [parition {}]", result.getRecordMetadata().topic(), result.getRecordMetadata().partition());
            } else {
                logger.error("Error sending message: {}", e.getMessage(), e);
            }
        });
    }

}

