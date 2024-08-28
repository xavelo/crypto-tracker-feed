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

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

@Component
public class CoinGeckoApiAdapter implements PriceService {

    private static final Logger logger = LogManager.getLogger(CoinGeckoApiAdapter.class);

    private static final String PRICE_URL_TEMPLATE = "%s/simple/price?ids=%s&vs_currencies=%s&x_cg_demo_api_key=%s";

    //TODO - add to a seccret
    private static final String COINGECKO_API_KEY = "CG-k26E4qpRpFckxcfdrcq3DTH7";

    private final String COINGECKO_BASE_URL;

    public CoinGeckoApiAdapter(@Value("${coingecko.api.url}") String apiUrl) {
        this.COINGECKO_BASE_URL = apiUrl;
    }

    @Override
    public Price fetchPrice(Coin coin, Currency currency) {

        String url = String.format(PRICE_URL_TEMPLATE, COINGECKO_BASE_URL, coin.getFullName(), currency.name().toLowerCase(), COINGECKO_API_KEY);
        logger.info("GET request to CoinGecko API URL {}", url);

        HttpResponse<JsonNode> response = Unirest.get(url)
                .header("accept", "application/json")
                .asJson();

        String coinPrice = "0";
        if (response.isSuccess()) {
            JsonNode jsonNode = response.getBody();
            System.out.println("Parsed JSON:");
            System.out.println(jsonNode);

            coinPrice = String.valueOf(jsonNode.getObject().getJSONObject(coin.getFullName().toLowerCase()).getInt(currency.name().toLowerCase()));
            logger.info("received {} price for {} coin", coinPrice, coin.getFullName());
            System.out.println("Bitcoin current price: " + coinPrice);
        } else {
            System.out.println("Failed to retrieve data. Status code: " + response.getStatus());
        }

        return new Price(coin, BigDecimal.valueOf(Long.valueOf(coinPrice)), currency, Instant.now());

    }

}