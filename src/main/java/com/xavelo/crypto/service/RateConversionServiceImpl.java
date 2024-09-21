package com.xavelo.crypto.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.xavelo.crypto.Currency;
import com.xavelo.crypto.adapter.RateConversionAdapter;

@Component
public class RateConversionServiceImpl implements RateConversionService {

    private final RateConversionAdapter rateConversionAdapter;

    public RateConversionServiceImpl(RateConversionAdapter rateConversionAdapter) {
        this.rateConversionAdapter = rateConversionAdapter;
    }

    @Override
    public BigDecimal convert(Currency from, BigDecimal amount) {
        double rate = rateConversionAdapter.getRate(from);
        return BigDecimal.valueOf(rate).multiply(amount);
    }

}
