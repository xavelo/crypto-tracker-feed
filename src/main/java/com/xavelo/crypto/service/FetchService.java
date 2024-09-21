package com.xavelo.crypto.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xavelo.crypto.adapter.PriceFetchException;
import com.xavelo.crypto.data.Coin;
import com.xavelo.crypto.data.Currency;
import com.xavelo.crypto.data.Price;

import org.springframework.stereotype.Service;

@Service
public interface FetchService {

    public Price fetchPrice(Coin coin, Currency currency) throws PriceFetchException;

    public Price fetchAndPublishPrice(Coin coin, Currency currency) throws PriceFetchException, JsonProcessingException;

}
