package com.xavelo.crypto.service;

import com.xavelo.crypto.adapter.PriceFetchException;
import com.xavelo.crypto.data.Coin;
import com.xavelo.crypto.data.Currency;
import com.xavelo.crypto.data.Price;

import org.springframework.stereotype.Service;

@Service
public interface PriceService {

    public Price fetchPrice(Coin coin, Currency currency) throws PriceFetchException;

}
