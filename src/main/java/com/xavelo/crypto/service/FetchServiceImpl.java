package com.xavelo.crypto.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xavelo.crypto.Coin;
import com.xavelo.crypto.Currency;
import com.xavelo.crypto.Price;
import com.xavelo.crypto.adapter.PriceFetchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FetchServiceImpl implements FetchService {

    @Autowired
    PriceService priceService;

    @Autowired
    PublishService publishService;

    @Override
    public Price fetchPrice(Coin coin, Currency currency) throws PriceFetchException {
        return priceService.fetchPrice(coin, currency);
    }

    @Override
    public Price fetchAndPublishPrice(Coin coin, Currency currency) throws PriceFetchException, JsonProcessingException {
        Price price = priceService.fetchPrice(coin, currency);
        publishService.publishPrice(price);
        return price;
    }

}
