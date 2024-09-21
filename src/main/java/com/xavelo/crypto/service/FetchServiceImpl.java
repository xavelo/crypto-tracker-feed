package com.xavelo.crypto.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xavelo.crypto.Coin;
import com.xavelo.crypto.Currency;
import com.xavelo.crypto.Price;
import com.xavelo.crypto.adapter.BitpandaApiAdapter;
import com.xavelo.crypto.adapter.PriceFetchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.slf4j.Logger; // Import Logger
import org.slf4j.LoggerFactory; // Import LoggerFactory

@Component
@Primary
public class FetchServiceImpl implements FetchService {

    private static final Logger logger = LoggerFactory.getLogger(FetchServiceImpl.class); // Create logger instance

    private final PriceService priceService;
    private final PublishService publishService;
    private final BitpandaApiAdapter bitpandaApiAdapter;

    public FetchServiceImpl(PriceService priceService, PublishService publishService, BitpandaApiAdapter bitpandaApiAdapter) {
        this.priceService = priceService;
        this.publishService = publishService;
        this.bitpandaApiAdapter = bitpandaApiAdapter;
    }

    @Override
    public Price fetchPrice(Coin coin, Currency currency) throws PriceFetchException {
        // test
        Price p = bitpandaApiAdapter.fetchPrice(coin, currency);
        logger.info("Price obtained from Bitpanda: {}", p);
        logger.info("Fetching price for {} in {}", coin, currency); // Log fetching price
        return priceService.fetchPrice(coin, currency);
    }

    @Override
    public Price fetchAndPublishPrice(Coin coin, Currency currency) throws PriceFetchException, JsonProcessingException {
        logger.info("Fetching and publishing price for {} in {}", coin, currency); // Log fetching and publishing price
        Price price = priceService.fetchPrice(coin, currency);
        publishService.publishPrice(price);
        return price;
    }

}
