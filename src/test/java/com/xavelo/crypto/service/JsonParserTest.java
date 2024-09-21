package com.xavelo.crypto.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonParserTest {

    private static final String JSON_RESPONSE = "{\n" +
            "    \"data\": [\n" +
            "        {\n" +
            "            \"type\": \"asset_price\",\n" +
            "            \"attributes\": {\n" +
            "                \"pid\": \"b86c034b-efe3-11eb-b56f-0691764446a7\",\n" +
            "                \"price\": \"56729.06\",\n" +
            "                \"price_amount_changes\": {\n" +
            "                    \"day\": \"391.3897709986239618\",\n" +
            "                    \"week\": \"2532.1947905452693480\",\n" +
            "                    \"month\": \"2516.3522507454478077\",\n" +
            "                    \"year\": \"31758.1127886633071682\"\n" +
            "                },\n" +
            "                \"price_percentage_changes\": {\n" +
            "                    \"day\": \"0.69\",\n" +
            "                    \"week\": \"4.67\",\n" +
            "                    \"month\": \"4.64\",\n" +
            "                    \"year\": \"127.18\"\n" +
            "                },\n" +
            "                \"circulating_supply\": \"19756762.00000000\",\n" +
            "                \"market_cap\": \"1120782534092.43155121\"\n" +
            "            },\n" +
            "            \"id\": \"1\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    @Test
    public void testExtractPrice() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(JSON_RESPONSE);
            String price = rootNode.path("data").get(0).path("attributes").path("price").asText();
            
            assertEquals("56729.06", price, "The extracted price should match the expected value");
        } catch (Exception e) {
            fail("Exception occurred during JSON parsing: " + e.getMessage());
        }
    }

    @Test
    public void testExtractPriceWithInvalidJson() {
        String invalidJson = "{ \"data\": [] }";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(invalidJson);
            String price = rootNode.path("data").path(0).path("attributes").path("price").asText();
            
            assertTrue(price.isEmpty(), "The extracted price should be empty for invalid JSON");
        } catch (Exception e) {
            fail("Exception occurred during JSON parsing: " + e.getMessage());
        }
    }
}
