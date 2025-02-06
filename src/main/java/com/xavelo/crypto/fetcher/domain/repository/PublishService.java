package com.xavelo.crypto.fetcher.domain.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xavelo.crypto.fetcher.domain.model.Price;
import com.xavelo.crypto.fetcher.domain.model.CoinData;

import org.springframework.stereotype.Service;

@Service
public interface PublishService {

    public void publishPrice(Price price) throws JsonProcessingException;

    public void publishCoinData(CoinData coinData) throws JsonProcessingException;

}
