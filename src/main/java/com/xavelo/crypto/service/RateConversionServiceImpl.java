package com.xavelo.crypto.service;

import org.springframework.stereotype.Component;

import com.xavelo.crypto.adapter.RateConversionAdapter;
import com.xavelo.crypto.data.Currency;

@Component
public class RateConversionServiceImpl implements RateConversionService {

    private final RateConversionAdapter rateConversionAdapter;

    public RateConversionServiceImpl(RateConversionAdapter rateConversionAdapter) {
        this.rateConversionAdapter = rateConversionAdapter;
    }
    
    @Override
    public double convertToUSD(Currency from, double amount) {
        double rate = rateConversionAdapter.getRate(from);        
        return amount / rate;
    }

}
