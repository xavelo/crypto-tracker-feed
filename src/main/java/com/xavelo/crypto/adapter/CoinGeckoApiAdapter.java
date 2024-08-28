package com.xavelo.crypto.adapter;

import com.xavelo.crypto.Coin;
import com.xavelo.crypto.Currency;
import com.xavelo.crypto.Price;
import com.xavelo.crypto.service.PriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.Instant;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

@Component
public class CoinGeckoApiAdapter implements PriceService {

    private static final Logger logger = LoggerFactory.getLogger(CoinGeckoApiAdapter.class);

    //TODO - add to a seccret
    private static final String API_KEY = "CG-k26E4qpRpFckxcfdrcq3DTH7";

    private final RestTemplate restTemplate;
    private final String apiUrl;

    public CoinGeckoApiAdapter(RestTemplate restTemplate, @Value("${coingecko.api.url}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
    }

    @Override
    public Price fetchPrice(Coin coin, Currency currency) {

        String url = getUrl(coin, currency);
        HttpResponse<JsonNode> response = Unirest.get(url)
                .header("accept", "application/json")
                .asJson();

        String coinPrice = "0";
        if (response.isSuccess()) {
            JsonNode jsonNode = response.getBody();
            System.out.println("Parsed JSON:");
            System.out.println(jsonNode);

            // Example of accessing a specific field
            coinPrice = String.valueOf(jsonNode.getObject().getJSONObject(coin.getFullName()).getInt(currency.getFullName().toLowerCase()));
            logger.info("received {} price for {} coin", coinPrice, coin.getFullName());
            System.out.println("Bitcoin current price: " + coinPrice);
        } else {
            System.out.println("Failed to retrieve data. Status code: " + response.getStatus());
        }

        return new Price(coin, BigDecimal.valueOf(Long.valueOf(coinPrice)), currency, Instant.now());

    }

    private String getUrl(Coin coin, Currency currency) {
        String url = String.format("%s/simple/price?ids=%s&vs_currencies=%s&x_cg_demo_api_key=%s", apiUrl, coin.getFullName(), currency.name().tolowercase(), API_KEY);
        logger.info("GET request to CoinGecko API URL {}", url);
        return url;
    }

}