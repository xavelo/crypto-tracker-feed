package com.xavelo.crypto.adapter;

import com.xavelo.crypto.Coin;
import com.xavelo.crypto.Currency;
import com.xavelo.crypto.Price;
import com.xavelo.crypto.service.PriceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.Instant;

@Component
public class CoinGeckoApiAdapter implements PriceService {

    private static final Logger logger = LogManager.getLogger(CoinGeckoApiAdapter.class);

    //TODO - add to a seccret
    private static final String API_KEY = "CG-k26E4qpRpFckxcfdrcq3DTH7";

    private final RestTemplate restTemplate;
    private final String apiUrl;

    public CoinGeckoApiAdapter(RestTemplate restTemplate, @Value("${coingecko.api.url}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
    }

    @Override
    public Price fetchPrice(Coin coin, Currency currency) throws PriceFetchException {
        try {
            String url = String.format("%s/simple/price?ids=%s&vs_currencies=%s&x_cg_demo_api_key=%s", apiUrl, coin.getFullName(), currency.name().toLowerCase(), API_KEY);
            logger.debug("CoinGecko API URL {}", url);
            CoinGeckoResponse response = restTemplate.getForObject(url, CoinGeckoResponse.class);

            if (response == null || response.getPrice(coin, currency) == null) {
                throw new PriceFetchException("Unable to fetch price from CoinGecko API");
            }

            logger.debug("CoinGeckoResponse response {}", response);

            BigDecimal price = response.getPrice(coin, currency);
            return new Price(coin, price, currency, Instant.now());
        } catch (Exception e) {
            throw new PriceFetchException("Error fetching price from CoinGecko API", e);
        }
    }

}