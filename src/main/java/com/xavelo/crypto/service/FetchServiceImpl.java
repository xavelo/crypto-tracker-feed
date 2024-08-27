package com.xavelo.crypto.service;

import com.xavelo.crypto.Coin;
import com.xavelo.crypto.Currency;
import com.xavelo.crypto.Price;

import java.math.BigDecimal;
import java.time.Instant;

public class FetchServiceImpl implements FetchService {

    @Override
    public Price fetchPrice(Coin coin) {

        // TODO fetch price from external API (e.g. CoinGecko)
        return new Price(coin, BigDecimal.valueOf(60000.0), Currency.USD, Instant.now());

    }

}
