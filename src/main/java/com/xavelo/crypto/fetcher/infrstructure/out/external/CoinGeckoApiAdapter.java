package com.xavelo.crypto.fetcher.infrstructure.out.external;

import com.xavelo.crypto.fetcher.domain.model.Coin;
import com.xavelo.crypto.fetcher.domain.model.Currency;
import com.xavelo.crypto.fetcher.domain.model.Data;
import com.xavelo.crypto.fetcher.domain.model.Price;
import com.xavelo.crypto.fetcher.domain.repository.DataService;
import com.xavelo.crypto.fetcher.domain.repository.PriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.springframework.stereotype.Component;

@Component
public class CoinGeckoApiAdapter implements PriceService, DataService {

    private static final Logger logger = LoggerFactory.getLogger(CoinGeckoApiAdapter.class);

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
        logger.debug("GET request to CoinGecko API URL {}", url);

        HttpResponse<JsonNode> response = Unirest.get(url)
                .header("accept", "application/json")
                .asJson();

        String coinPrice = "0";
        if (response.isSuccess()) {
            JsonNode jsonNode = response.getBody();
            logger.debug("Parsed JSON: {}", jsonNode);

            coinPrice = String.valueOf(jsonNode.getObject().getJSONObject(coin.getFullName().toLowerCase()).getInt(currency.name().toLowerCase()));
            logger.info("received {} price for {} coin", coinPrice, coin.getFullName());
        } else {
            logger.error("Failed to retrieve data. Status code: {}", response.getStatus());
        }

        return new Price(coin, Double.valueOf(coinPrice), currency, Date.from(Instant.now().atZone(ZoneId.of("Europe/Madrid")).toInstant()));

    }

    @Override
    public List<Data> getData(List<Coin> coins) {
        List<Data> data = new ArrayList<>(coins.size());

        String url = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&ids=bitcoin,ethereum,cardano";
        logger.info("GET request to CoinGecko API URL {}", url);

        HttpResponse<JsonNode> response = Unirest.get(url)
                .header("x-cg-demo-api-key", "CG-1Jad8ug4ejaqePVGrQCeLaTx")
                .header("accept", "application/json")
                .asJson();

        String coinPrice = "0";
        if (response.isSuccess()) {
            JsonNode jsonNode = response.getBody();
            logger.info("Parsed JSON: {}", jsonNode);

            //coinPrice = String.valueOf(jsonNode.getObject().getJSONObject(coin.getFullName().toLowerCase()).getInt(currency.name().toLowerCase()));
            //logger.info("received {} price for {} coin", coinPrice, coin.getFullName());
        } else {
            logger.error("Failed to retrieve data. Status code: {}", response.getStatus());
        }

        return data;
    }

}