package com.xavelo.crypto.service;

import com.xavelo.crypto.Coin;
import com.xavelo.crypto.Currency;
import com.xavelo.crypto.Price;
import com.xavelo.crypto.adapter.PriceFetchException;
import org.springframework.stereotype.Service;

@Service
public interface FetchService {

    public Price fetchPrice(Coin coin, Currency currency) throws PriceFetchException;

    public Price fetchAndPublishPrice(Coin coin, Currency currency) throws PriceFetchException;

}
