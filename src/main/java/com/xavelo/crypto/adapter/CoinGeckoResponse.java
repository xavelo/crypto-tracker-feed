package com.xavelo.crypto.adapter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xavelo.crypto.Coin;
import com.xavelo.crypto.Currency;

import java.math.BigDecimal;
import java.util.Map;

public class CoinGeckoResponse {

    @JsonProperty
    private Map<String, Map<String, BigDecimal>> prices;

    public BigDecimal getPrice(Coin coin, Currency currency) {
        Map<String, BigDecimal> coinPrices = prices.get(coin.name().toLowerCase());
        return coinPrices != null ? coinPrices.get(currency.name().toLowerCase()) : null;
    }

}
