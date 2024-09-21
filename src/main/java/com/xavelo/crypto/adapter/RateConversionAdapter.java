package com.xavelo.crypto.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.xavelo.crypto.data.Currency;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

@Component
public class RateConversionAdapter {    

    private static final Logger logger = LoggerFactory.getLogger(RateConversionAdapter.class);

    public double getRate(Currency currency) {        
        String url = "https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_W12N79k8qvMlG1HJLxx7YvoAOpPtwlXrKyH6E2cm";
        HttpResponse<JsonNode> response = Unirest.get(url)
                .header("accept", "application/json")
                .asJson();

        double exchangeRate = 0;
        if (response.isSuccess()) {
            JsonNode jsonNode = response.getBody();            
            exchangeRate = jsonNode.getObject().getJSONObject("data").getDouble(currency.toString());
            logger.debug("Received exchange rate for {}: {}", currency.toString(), exchangeRate);            
        } else {
            logger.error("Failed to retrieve exchange rate for {}. Status code: {}", currency.toString(), response.getStatus());
        }
        return exchangeRate;

    }

}
