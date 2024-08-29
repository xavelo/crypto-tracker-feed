package com.xavelo.crypto.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaAdapter.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void publishPriceUpdate(String topic, String key, String message) {
        LOGGER.info("-> topic '{}' --- key '{}' -  message '{}'", topic, key, message);
        kafkaTemplate.send(topic, key, message);
    }

}

