package com.xavelo.crypto.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xavelo.crypto.PriceSerializer;
import com.xavelo.crypto.adapter.KafkaAdapter;
import com.xavelo.crypto.data.Price;

import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId; // {{ edit_1 }}

@Component
public class PublishServiceImpl implements PublishService {

    private static final Logger logger = LoggerFactory.getLogger(PublishServiceImpl.class);

    private static final String CRYPTO_PRICE_UPDATES_TOPICS = "crypto-price-updates-topic";

    private final KafkaAdapter kafkaAdapter;

    public PublishServiceImpl(KafkaAdapter kafkaAdapter) {
        this.kafkaAdapter = kafkaAdapter;
    }

    @Override
    public void publishPrice(Price price) throws JsonProcessingException {
        String message = PriceSerializer.serializeToJson(price);
        String timestamp = ZonedDateTime.ofInstant(price.getTimestamp(), ZoneId.of("Europe/Madrid")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        logger.info("publishing price update for {} timestamp {}", price.getCoin(), timestamp);
        kafkaAdapter.publishPriceUpdate(CRYPTO_PRICE_UPDATES_TOPICS, price.getCoin().name(), message);
    }
}
