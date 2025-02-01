package com.xavelo.crypto.fetcher.domain.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xavelo.crypto.fetcher.application.PriceFetchException;
import com.xavelo.crypto.fetcher.domain.model.Coin;
import com.xavelo.crypto.fetcher.domain.model.Currency;
import com.xavelo.crypto.fetcher.domain.model.Price;

import org.springframework.stereotype.Service;

@Service
public interface FetchService {

    public Price fetchPrice(Coin coin, Currency currency) throws PriceFetchException;

    public Price fetchAndPublishPrice(Coin coin, Currency currency) throws PriceFetchException, JsonProcessingException;

}
