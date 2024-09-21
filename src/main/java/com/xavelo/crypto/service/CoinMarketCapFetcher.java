package com.xavelo.crypto.service;

import com.xavelo.crypto.Price; // Add this import statement
import com.xavelo.crypto.Coin; 
import com.xavelo.crypto.Currency;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject; // Add this import statement
import org.slf4j.Logger; // Add this import
import org.slf4j.LoggerFactory; // Add this import
import org.springframework.stereotype.Component;

@Component
public class CoinMarketCapFetcher implements FetchService {

    private static final Logger logger = LoggerFactory.getLogger(CoinMarketCapFetcher.class); // Initialize logger

    @Override
    public Price fetchPrice(Coin coin, Currency currency) {    
        double price = getCoinMarketCapData();    
        return new Price(); 
    }

    @Override
    public Price fetchAndPublishPrice(Coin coin, Currency currency) {
        return new Price(); 
    }

    private double getCoinMarketCapData() {
        try {
            // Define the API endpoint
            String urlString = "https://coinmarketcap.com";
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            logger.info("Response: " + response.toString());
            // Parse the JSON response
            JSONObject jsonResponse = new JSONObject(response.toString());
            double bitcoinPrice = jsonResponse.getJSONArray("data")
                                               .getJSONObject(0)
                                               .getJSONObject("quote")
                                               .getJSONObject("USD")
                                               .getDouble("price"); 

            logger.info("Bitcoin Price: " + bitcoinPrice);
            return bitcoinPrice;
        } catch (Exception e) {
            logger.error("Error fetching CoinMarketCap data: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

}
