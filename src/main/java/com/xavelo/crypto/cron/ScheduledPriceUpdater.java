package com.xavelo.crypto.cron;

import com.xavelo.crypto.Coin;
import com.xavelo.crypto.Currency;
import com.xavelo.crypto.adapter.PriceFetchException;
import com.xavelo.crypto.service.PriceService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledPriceUpdater {

    private final PriceService priceService;

    public ScheduledPriceUpdater(PriceService priceService) {
        this.priceService = priceService;
    }

    @Scheduled(fixedRate = 60000) // Run every minute
    public void scheduledPriceUpdate() throws PriceFetchException {
        priceService.fetchPrice(Coin.BTC, Currency.USD);
        priceService.fetchPrice(Coin.ETH, Currency.USD);
    }

}
