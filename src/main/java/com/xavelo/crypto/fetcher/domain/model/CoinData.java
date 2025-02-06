package com.xavelo.crypto.fetcher.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

public class CoinData {
    @JsonProperty
    private String id;
    @JsonProperty
    private String symbol;
    @JsonProperty
    private String name;
    @JsonProperty
    private String image;
    @JsonProperty
    private double currentPrice;
    @JsonProperty
    private long marketCap;
    @JsonProperty
    private int marketCapRank;
    @JsonProperty
    private long fullyDilutedValuation;
    @JsonProperty
    private long totalVolume;
    @JsonProperty
    private double high24h;
    @JsonProperty
    private double low24h;
    @JsonProperty
    private double priceChange24h;
    @JsonProperty
    private double priceChangePercentage24h;
    @JsonProperty
    private double marketCapChange24h;
    @JsonProperty
    private double marketCapChangePercentage24h;
    @JsonProperty
    private double circulatingSupply;
    @JsonProperty
    private Double totalSupply;
    @JsonProperty
    private Double maxSupply;
    @JsonProperty
    private double ath;
    @JsonProperty
    private double athChangePercentage;
    @JsonProperty
    private String athDate;
    @JsonProperty
    private double atl;
    @JsonProperty
    private double atlChangePercentage;
    @JsonProperty
    private String atlDate;
    @JsonProperty
    private Roi roi;
    @JsonProperty
    private String lastUpdated;

    // Nested class for ROI
    public static class Roi {
        @JsonProperty
        private double times;
        @JsonProperty
        private String currency;
        @JsonProperty
        private double percentage;

        // Getters and setters
    }

    // Getters and setters for all fields (omitted for brevity)

    public static List<CoinData> parseJson(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, mapper.getTypeFactory()
                .constructCollectionType(List.class, CoinData.class));
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public String getSymbol() {
        return symbol;
    }
}
