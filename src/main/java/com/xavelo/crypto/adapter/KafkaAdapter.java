package com.xavelo.crypto.adapter;

import com.xavelo.crypto.service.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaAdapter.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void produceMessage(String topic, Message message) {
        LOGGER.info("-> topic '{}' --- message '{}'", topic, message);
        kafkaTemplate.send(topic, message.getValue());
    }

}

