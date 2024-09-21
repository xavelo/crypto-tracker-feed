package com.xavelo.crypto.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.xavelo.crypto.Currency;

@Service
public interface RateConversionService {
    // converts amount to USD
    BigDecimal convert(Currency from, BigDecimal amount);
}
