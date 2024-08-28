package com.xavelo.crypto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class PriceSerializer {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static String serializeToJson(Price price) throws JsonProcessingException {
        return objectMapper.writeValueAsString(price);
    }

    public static Price deserializeFromJson(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, Price.class);
    }
}
