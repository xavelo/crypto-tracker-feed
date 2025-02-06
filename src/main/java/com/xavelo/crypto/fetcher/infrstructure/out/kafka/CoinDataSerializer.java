package com.xavelo.crypto.fetcher.infrstructure.out.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.xavelo.crypto.fetcher.domain.model.CoinData;

public class CoinDataSerializer {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static String serializeToJson(CoinData coinData) throws JsonProcessingException {
        return objectMapper.writeValueAsString(coinData);
    }

}
