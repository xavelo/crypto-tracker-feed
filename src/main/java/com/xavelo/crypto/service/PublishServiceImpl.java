package com.xavelo.crypto.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xavelo.crypto.Price;
import com.xavelo.crypto.PriceSerializer;
import com.xavelo.crypto.adapter.KafkaAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class PublishServiceImpl implements PublishService {

    private static final Logger logger = LoggerFactory.getLogger(PublishServiceImpl.class);

    private static final String CRYPTO_PRICE_UPDATES_TOPICS = "crypto-price-updates-topic";

    private final KafkaAdapter kafkaAdapter;

    @Autowired
    public PublishServiceImpl(KafkaAdapter kafkaAdapter) {
        this.kafkaAdapter = kafkaAdapter;
    }

    @Override
    public void publishPrice(Price price) throws JsonProcessingException {
        String message = PriceSerializer.serializeToJson(price);
        logger.info("publishing price update {}", message);
        kafkaAdapter.publishPriceUpdate(CRYPTO_PRICE_UPDATES_TOPICS, price.getCoin().name(), message);
    }
}
