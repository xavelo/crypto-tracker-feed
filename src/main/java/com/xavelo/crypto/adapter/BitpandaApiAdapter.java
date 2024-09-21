package com.xavelo.crypto.adapter;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xavelo.crypto.Coin;
import com.xavelo.crypto.Currency;
import com.xavelo.crypto.Price;
import com.xavelo.crypto.service.PriceService;


@Component
public class BitpandaApiAdapter implements PriceService {

    private static final Logger logger = LoggerFactory.getLogger(BitpandaApiAdapter.class);

    private static final String BITPANDA_API_URL = "https://api.bitpanda.com/v1/assets/prices";

    @Override
    public Price fetchPrice(Coin coin, Currency currency) {
        logger.info("Fetching price from Bitpanda for {} in {}", coin, currency);
        String assetId = getAssetId(coin);
        
        HttpClient client = HttpClient.newHttpClient();                
        String requestBody = "{\"asset_ids\":[\"" + assetId + "\"]}";                
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BITPANDA_API_URL))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();
        
        try {            
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("Response: " + response.body());           
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.body());
            String price = rootNode.path("data").get(0).path("attributes").path("price").asText();                        
            logger.info("Price: {}", price);
            return new Price(coin, new BigDecimal(price), currency, Instant.now());
        } catch (IOException | InterruptedException e) {
            logger.error("Error fetching price from Bitpanda: {}", e.getMessage());
            // TODO - handle exceptions appropriately
            return null;
        }        
    }

    private String getAssetId(Coin coin) {
        switch (coin) {
            case BTC:
                return "1";
            case ETH:
                return "5";
            default:
                return null;
        }
    }

}

