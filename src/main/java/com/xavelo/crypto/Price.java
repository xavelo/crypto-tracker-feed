package com.xavelo.crypto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Price {
    private Coin coin;
    private BigDecimal price;
    private Currency currency;
    private Instant timestamp;
}
