package com.xavelo.crypto.adapter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CoinGeckoResponse {
    private List<CoinData> data;

    @Data
    public static class CoinData {
        @JsonProperty("current_price")
        private BigDecimal currentPrice;

        // You can add other fields here if needed in the future
        private String id;
        private String symbol;
        private String name;
    }

    // Convenience method to get the first coin's current price
    public BigDecimal getCurrentPrice() {
        if (data != null && !data.isEmpty()) {
            return data.get(0).getCurrentPrice();
        }
        return null;
    }
}