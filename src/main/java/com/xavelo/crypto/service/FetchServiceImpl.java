package com.xavelo.crypto.service;

import com.xavelo.crypto.Coin;
import com.xavelo.crypto.Currency;
import com.xavelo.crypto.Price;
import com.xavelo.crypto.adapter.PriceFetchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;

@Component
public class FetchServiceImpl implements FetchService {

    @Autowired
    PriceService priceService;

    @Override
    public Price fetchPrice(Coin coin, Currency currency) throws PriceFetchException {
        return priceService.fetchPrice(coin, currency);
    }

}
