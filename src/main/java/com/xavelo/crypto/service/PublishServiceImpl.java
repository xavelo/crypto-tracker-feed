package com.xavelo.crypto.service;

import com.xavelo.crypto.Price;
import com.xavelo.crypto.adapter.KafkaAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class PublishServiceImpl implements PublishService {

    private static final Logger logger = LoggerFactory.getLogger(PublishServiceImpl.class);

    @Autowired
    KafkaAdapter kafkaAdapter;

    @Override
    public void publishPrice(Price price) {
        logger.info("publishing price update for {}: {}{}", price.getCoin(), price.getPrice(), price.getCurrency());
        kafkaAdapter.produceMessage("test-topic", price.getCoin() + " " + price.getPrice() + "  " + price.getCurrency());
    }
}
