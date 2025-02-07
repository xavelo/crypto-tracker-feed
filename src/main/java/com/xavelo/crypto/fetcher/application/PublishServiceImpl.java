package com.xavelo.crypto.fetcher.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xavelo.crypto.fetcher.domain.repository.PublishService;
import com.xavelo.crypto.fetcher.infrastructure.out.kafka.KafkaAdapter;
import com.xavelo.crypto.fetcher.infrastructure.out.kafka.PriceSerializer;
import com.xavelo.crypto.fetcher.infrastructure.out.kafka.CoinDataSerializer;
import com.xavelo.crypto.fetcher.domain.model.CoinData;
import com.xavelo.crypto.fetcher.domain.model.Price;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@AllArgsConstructor
public class PublishServiceImpl implements PublishService {

    private static final Logger logger = LoggerFactory.getLogger(PublishServiceImpl.class);

    private static final String CRYPTO_PRICE_UPDATES_TOPIC = "crypto-price-updates-topic";
    private static final String CRYPTO_DATA_TOPIC = "crypto-data-topic";

    private final KafkaAdapter kafkaAdapter;

    @Override
    public void publishPrice(Price price) throws JsonProcessingException {
        logger.debug("-> publishPrice {}", price);
        String message = PriceSerializer.serializeToJson(price);        
        kafkaAdapter.publishMessage(CRYPTO_PRICE_UPDATES_TOPIC, price.getCoin().name(), message);
    }

    @Override
    public void publishCoinData(CoinData coinData) throws JsonProcessingException {
        logger.debug("-> publishCoinData {}", coinData);
        String message = CoinDataSerializer.serializeToJson(coinData);
        kafkaAdapter.publishMessage(CRYPTO_DATA_TOPIC, coinData.getSymbol(), message);
    }
}
