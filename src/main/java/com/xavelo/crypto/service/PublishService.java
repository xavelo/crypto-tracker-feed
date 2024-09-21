package com.xavelo.crypto.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xavelo.crypto.data.Price;

import org.springframework.stereotype.Service;

@Service
public interface PublishService {

    public void publishPrice(Price price) throws JsonProcessingException;

}
