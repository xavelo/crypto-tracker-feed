package com.xavelo.crypto.fetcher.infrstructure.out.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit; 

import com.xavelo.crypto.fetcher.domain.model.Currency;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

@Component
public class FreeCurrencyApiAdapter {

    private static final Logger logger = LoggerFactory.getLogger(FreeCurrencyApiAdapter.class);
    private Map<Currency, CachedRate> rateCache = new HashMap<>();
    private static final long TTL = TimeUnit.MINUTES.toMillis(10);

    public double getRate(Currency currency) {       
        
        // Check if the rate is already cached and not expired
        CachedRate cachedRate = rateCache.get(currency);
        if (cachedRate != null && !cachedRate.isExpired(TTL)) {
            logger.info("getting rate from cache:{}", cachedRate.getRate());
            return cachedRate.getRate(); // Return cached rate
        }

        String url = "https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_W12N79k8qvMlG1HJLxx7YvoAOpPtwlXrKyH6E2cm";
        HttpResponse<JsonNode> response = Unirest.get(url)
                .header("accept", "application/json")
                .asJson();

        double exchangeRate = 0;
        if (response.isSuccess()) {
            JsonNode jsonNode = response.getBody();            
            exchangeRate = jsonNode.getObject().getJSONObject("data").getDouble(currency.toString());
            rateCache.put(currency, new CachedRate(exchangeRate));
            logger.debug("Received exchange rate for {}: {}", currency.toString(), exchangeRate);            
        } else {
            logger.error("Failed to retrieve exchange rate for {}. Status code: {}", currency.toString(), response.getStatus());
        }
        return exchangeRate;

    }

}

class CachedRate {
    private final double rate;
    private final long timestamp;

    public CachedRate(double rate) {
        this.rate = rate;
        this.timestamp = System.currentTimeMillis();
    }

    public double getRate() {
        return rate;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public boolean isExpired(long ttl) {
        return (System.currentTimeMillis() - timestamp) > ttl; // Check if expired
    }
}
