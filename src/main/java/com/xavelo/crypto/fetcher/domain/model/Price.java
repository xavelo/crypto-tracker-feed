package com.xavelo.crypto.fetcher.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    private Date timestamp;
}
