package com.xavelo.crypto.fetcher.domain.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xavelo.crypto.fetcher.domain.model.Price;

import org.springframework.stereotype.Service;

@Service
public interface PublishService {

    public void publishPrice(Price price) throws JsonProcessingException;

}
