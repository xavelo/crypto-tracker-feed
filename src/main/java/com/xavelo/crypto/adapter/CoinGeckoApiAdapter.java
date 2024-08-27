package com.xavelo.crypto.adapter;

import com.xavelo.crypto.Coin;
import com.xavelo.crypto.Currency;
import com.xavelo.crypto.Price;
import com.xavelo.crypto.service.PriceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.Instant;

@Service
public class CoinGeckoApiAdapter implements PriceService {

    private final RestTemplate restTemplate;
    private final String apiUrl;

    public CoinGeckoApiAdapter(RestTemplate restTemplate, @Value("${coingecko.api.url}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
    }

    @Override
    public Price fetchPrice(Coin coin, Currency currency) throws PriceFetchException {
        try {
            String url = String.format("%s/simple/price?ids=%s&vs_currencies=%s",
                    apiUrl, coin.name().toLowerCase(), currency.name().toLowerCase());

            CoinGeckoResponse response = restTemplate.getForObject(url, CoinGeckoResponse.class);

            if (response == null || response.getPrice(coin, currency) == null) {
                throw new PriceFetchException("Unable to fetch price from CoinGecko API");
            }

            BigDecimal price = response.getPrice(coin, currency);
            return new Price(coin, price, currency, Instant.now());
        } catch (Exception e) {
            throw new PriceFetchException("Error fetching price from CoinGecko API", e);
        }
    }

}