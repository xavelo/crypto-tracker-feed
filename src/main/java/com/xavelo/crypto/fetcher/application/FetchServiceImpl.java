package com.xavelo.crypto.fetcher.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xavelo.crypto.fetcher.domain.model.Coin;
import com.xavelo.crypto.fetcher.domain.model.Currency;
import com.xavelo.crypto.fetcher.domain.model.Price;

import com.xavelo.crypto.fetcher.domain.repository.FetchService;
import com.xavelo.crypto.fetcher.domain.repository.PriceService;
import com.xavelo.crypto.fetcher.domain.repository.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Primary
public class FetchServiceImpl implements FetchService {

    private static final Logger logger = LoggerFactory.getLogger(FetchServiceImpl.class); // Create logger instance

    private final PriceService priceService;
    private final PublishService publishService;

    @Autowired
    public FetchServiceImpl(PriceService priceService, PublishService publishService) {
        this.priceService = priceService;
        this.publishService = publishService;        
    }

    @Override
    public Price fetchPrice(Coin coin, Currency currency) throws PriceFetchException {                
        logger.debug("-> fetchPrice - coin {} - currency {}", coin, currency);
        return priceService.fetchPrice(coin, currency);
    }

    @Override
    public Price fetchAndPublishPrice(Coin coin, Currency currency) throws PriceFetchException, JsonProcessingException {
        logger.debug("-> fetchAndPublishPrice - coin{} - currency {}", coin, currency);
        Price price = priceService.fetchPrice(coin, currency);
        publishService.publishPrice(price);
        return price;
    }

}
