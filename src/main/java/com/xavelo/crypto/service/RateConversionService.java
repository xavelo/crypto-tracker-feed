package com.xavelo.crypto.service;

import org.springframework.stereotype.Service;

import com.xavelo.crypto.data.Currency;

@Service
public interface RateConversionService {    
    double convertToUSD(Currency from, double amount);
}
