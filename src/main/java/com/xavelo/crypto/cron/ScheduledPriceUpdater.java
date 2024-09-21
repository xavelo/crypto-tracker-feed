package com.xavelo.crypto.cron;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xavelo.crypto.Coin;
import com.xavelo.crypto.Currency;
import com.xavelo.crypto.adapter.PriceFetchException;
import com.xavelo.crypto.service.FetchService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledPriceUpdater {

    private final FetchService fetchService;

    public ScheduledPriceUpdater(FetchService fetchService) {
        this.fetchService = fetchService;
    }

    @Scheduled(fixedRate = 30000) // Run every 30 seconds
    public void scheduledPriceUpdate() throws PriceFetchException, JsonProcessingException {
        System.out.println("scheduledPriceUpdate...");
        fetchService.fetchAndPublishPrice(Coin.BTC, Currency.USD);
        fetchService.fetchAndPublishPrice(Coin.ETH, Currency.USD);
    }

}
