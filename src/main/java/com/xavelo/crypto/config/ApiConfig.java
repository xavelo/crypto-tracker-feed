package com.xavelo.crypto.config;

import com.xavelo.crypto.adapter.CoinGeckoApiAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.xavelo.crypto.service.PriceService;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApiConfig {

    @Value("${coingecko.api.url}")
    private String coinGeckoApiUrl;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public PriceService cryptoPriceService(RestTemplate restTemplate) {
        return new CoinGeckoApiAdapter(restTemplate, coinGeckoApiUrl);
    }

}
