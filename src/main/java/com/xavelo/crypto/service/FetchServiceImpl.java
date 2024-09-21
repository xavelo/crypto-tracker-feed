package com.xavelo.crypto.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xavelo.crypto.Coin;
import com.xavelo.crypto.Currency;
import com.xavelo.crypto.Price;
import com.xavelo.crypto.adapter.PriceFetchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger; // Import Logger
import org.slf4j.LoggerFactory; // Import LoggerFactory

@Component
public class FetchServiceImpl implements FetchService {

    private static final Logger logger = LoggerFactory.getLogger(FetchServiceImpl.class); // Create logger instance

    private final PriceService priceService; // Injected via constructor
    private final PublishService publishService; // Injected via constructor

    @Autowired
    public FetchServiceImpl(PriceService priceService, PublishService publishService) {
        this.priceService = priceService;
        this.publishService = publishService;
    }

    @Override
    public Price fetchPrice(Coin coin, Currency currency) throws PriceFetchException {
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
