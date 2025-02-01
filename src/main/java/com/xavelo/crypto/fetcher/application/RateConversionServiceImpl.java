package com.xavelo.crypto.fetcher.application;

import com.xavelo.crypto.fetcher.domain.repository.RateConversionService;
import org.springframework.stereotype.Component;

import com.xavelo.crypto.fetcher.infrstructure.out.external.RateConversionAdapter;
import com.xavelo.crypto.fetcher.domain.model.Currency;

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
