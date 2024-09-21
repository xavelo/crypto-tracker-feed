package com.xavelo.crypto.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

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
    public BigDecimal convertToUSD(Currency from, BigDecimal amount) {
        double rate = rateConversionAdapter.getRate(from);        
        return amount.divide(BigDecimal.valueOf(rate), new MathContext(amount.scale(), RoundingMode.HALF_UP));
    }

}
