package com.xavelo.crypto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Price {
    @JsonProperty("coin")
    private Coin coin;
    @JsonProperty("price")
    private BigDecimal price;
    @JsonProperty("currency")
    private Currency currency;
    @JsonProperty("timestamp")
    private Instant timestamp;
}
