package com.xavelo.crypto.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Price {
    @JsonProperty("coin")
    private Coin coin;
    @JsonProperty("price")
    private double price;
    @JsonProperty("currency")
    private Currency currency;
    @JsonProperty("timestamp")
    private String timestamp;
}
