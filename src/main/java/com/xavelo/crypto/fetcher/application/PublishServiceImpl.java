package com.xavelo.crypto.fetcher.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xavelo.crypto.PriceSerializer;
import com.xavelo.crypto.fetcher.domain.repository.PublishService;
import com.xavelo.crypto.fetcher.infrstructure.out.kafka.KafkaAdapter;
import com.xavelo.crypto.fetcher.domain.model.Price;

import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class PublishServiceImpl implements PublishService {

    private static final Logger logger = LoggerFactory.getLogger(PublishServiceImpl.class);

    private static final String CRYPTO_PRICE_UPDATES_TOPIC = "crypto-price-updates-topic";

    private final KafkaAdapter kafkaAdapter;

    public PublishServiceImpl(KafkaAdapter kafkaAdapter) {
        this.kafkaAdapter = kafkaAdapter;
    }

    @Override
    public void publishPrice(Price price) throws JsonProcessingException {
        logger.debug("-> publishPrice {}", price);
        String message = PriceSerializer.serializeToJson(price);        
        kafkaAdapter.publishPriceUpdate(CRYPTO_PRICE_UPDATES_TOPIC, price.getCoin().name(), message);
    }
}
