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

        String bitcoinPrice = "0";
        if (response.isSuccess()) {
            JsonNode jsonNode = response.getBody();
            System.out.println("Parsed JSON:");
            System.out.println(jsonNode);

            // Example of accessing a specific field
            bitcoinPrice = String.valueOf(jsonNode.getObject().getJSONObject("bitcoin").getInt("usd"));
            System.out.println("Bitcoin current price: " + bitcoinPrice);
        } else {
            System.out.println("Failed to retrieve data. Status code: " + response.getStatus());
        }

        return new Price(coin, BigDecimal.valueOf(Long.valueOf(bitcoinPrice)), currency, Instant.now());

    }

    private String getUrl(Coin coin, Currency currency) {
        String url = String.format("%s/simple/price?ids=%s&vs_currencies=%s&x_cg_demo_api_key=%s", apiUrl, coin.getFullName(), currency.name().toLowerCase(), API_KEY);
        logger.debug("CoinGecko API URL {}", url);
        return url;
    }


    public Price test(Coin coin, Currency currency) throws PriceFetchException {
        try {
            String url = String.format("%s/simple/price?ids=%s&vs_currencies=%s&x_cg_demo_api_key=%s", apiUrl, coin.getFullName(), currency.name().toLowerCase(), API_KEY);
            logger.debug("CoinGecko API URL {}", url);


            CoinGeckoResponse response = restTemplate.getForObject(url, CoinGeckoResponse.class);
            BigDecimal price = null;
            if (response != null) {
                price = response.getCurrentPrice();
            } else {
                throw new PriceFetchException("Unable to fetch price from CoinGecko API");
            }

            logger.debug("CoinGeckoResponse response {}", response);
            return new Price(coin, price, currency, Instant.now());
        } catch (Exception e) {
            String url = String.format("%s/simple/price?ids=%s&vs_currencies=%s&x_cg_demo_api_key=%s", apiUrl, coin.getFullName(), currency.name().toLowerCase(), API_KEY);
            logger.debug("CoinGecko API URL {}", url);
            throw new PriceFetchException("Error fetching price from CoinGecko API", e);
        }
    }

}