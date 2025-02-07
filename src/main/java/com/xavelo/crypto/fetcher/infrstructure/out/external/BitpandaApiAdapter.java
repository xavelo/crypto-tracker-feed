package com.xavelo.crypto.fetcher.infrstructure.out.external;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xavelo.crypto.fetcher.domain.model.Coin;
import com.xavelo.crypto.fetcher.domain.model.Currency;
import com.xavelo.crypto.fetcher.domain.model.Price;
import com.xavelo.crypto.fetcher.domain.repository.PriceService;
import com.xavelo.crypto.fetcher.domain.repository.RateConversionService;


@Component
@Primary
@AllArgsConstructor
public class BitpandaApiAdapter implements PriceService {

    private static final Logger logger = LoggerFactory.getLogger(BitpandaApiAdapter.class);

    private static final String BITPANDA_API_URL = "https://api.bitpanda.com/v1/assets/prices";

    private final RateConversionService rateConversionService;

    @Override
    public Price fetchPrice(Coin coin, Currency currency) {
        logger.info("Fetching price from Bitpanda for {} in {}", coin, currency);
        String assetId = getAssetId(coin);
        
        HttpClient client = HttpClient.newHttpClient();                
        String requestBody = "{\"asset_ids\":[\"" + assetId + "\"]}";
        logger.debug("requestBody: {}", requestBody);
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BITPANDA_API_URL))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();
        
        try {            
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            logger.debug("Response: " + response.body());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.body());
            String strPrice = rootNode.path("data").get(0).path("attributes").path("price").asText();                                    
            double price = Double.valueOf(strPrice);
            // All Bitpanda prices are in EUR
            if (currency == Currency.USD) {
                price = rateConversionService.convertToUSD(Currency.EUR, price);
            }
            return new Price(coin, price, currency, Date.from(Instant.now().atZone(ZoneId.of("Europe/Madrid")).toInstant()));
        } catch (IOException | InterruptedException e) {
            logger.error(e.toString());
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
            case SOL:
                return "135";
            case ADA:
                return "22";
            case BNB:
                return "46";
            case DOT:
                return "51";
            case AVAX:
                return "67";
            case FET:
                return "1112";
            case HBAR:
                return "62";
            case KAS:
                return "2970";
            case LINK:
                return "25";
            case XLM:
                return "20";
            case SUI:
                return "2923";
            case ICP:
                return "136";
            case LTC:
                return "3";
            case UNI:
                return "56";
            case AAVE:
                return "58";
            case NEAR:
                return "197";
            case RENDER:
                return "1090";
            case RUNE:
                return "1893";
            case TRX:
                return "31";
            case XRP:
                return "8";
            case APT:
                return "2852";
            case ONDO:
                return "3064";
            case OM:
                return "2621";
            
            default:
                return null;
        }
    }

}

