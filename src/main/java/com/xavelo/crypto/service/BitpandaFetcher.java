package com.xavelo.crypto.service;

import com.xavelo.crypto.Price;
import com.xavelo.crypto.Coin; 
import com.xavelo.crypto.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.net.URI;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

@Component
public class BitpandaFetcher implements FetchService {

    private static final Logger logger = LoggerFactory.getLogger(BitpandaFetcher.class); // Initialize logger

    @Override
    public Price fetchPrice(Coin coin, Currency currency) {    
        // Create the HTTP client
        HttpClient client = HttpClient.newHttpClient();
        
        // Prepare the request body
        String requestBody = "{\"asset_ids\":[\"1\"]}";
        
        // Create the POST request
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.bitpanda.com/v1/assets/prices"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();
        
        try {
            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            // Log the response
            logger.info("Response: " + response.body());
            
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.body());
            String price = rootNode.path("data").get(0).path("attributes").path("price").asText();
                        
            logger.info("Price: {}", price);
            return new Price(); // Update this line to return the actual Price object based on the response
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null; // Handle exceptions appropriately
        }
    }

    @Override
    public Price fetchAndPublishPrice(Coin coin, Currency currency) {
        return new Price(); 
    }

}
