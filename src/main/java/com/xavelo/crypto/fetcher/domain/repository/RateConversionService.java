package com.xavelo.crypto.fetcher.domain.repository;

import org.springframework.stereotype.Service;

import com.xavelo.crypto.fetcher.domain.model.Currency;

@Service
public interface RateConversionService {    
    double convertToUSD(Currency from, double amount);
}
