package com.xavelo.crypto.cron;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xavelo.crypto.adapter.PriceFetchException;
import com.xavelo.crypto.data.Coin;
import com.xavelo.crypto.data.Currency;
import com.xavelo.crypto.service.FetchService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledPriceUpdater {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledPriceUpdater.class);

    private final FetchService fetchService;    

    public ScheduledPriceUpdater(FetchService fetchService) {
        this.fetchService = fetchService;
    }

    @Scheduled(fixedRateString = "${crypto.fetcher.interval}")
    public void scheduledPriceUpdate() throws PriceFetchException, JsonProcessingException {
        logger.info("-----> scheduledPriceUpdate STARTED");
        fetchService.fetchAndPublishPrice(Coin.BTC, Currency.USD);
        fetchService.fetchAndPublishPrice(Coin.ETH, Currency.USD);
        fetchService.fetchAndPublishPrice(Coin.XRP, Currency.USD);
        fetchService.fetchAndPublishPrice(Coin.ADA, Currency.USD);
        fetchService.fetchAndPublishPrice(Coin.DOT, Currency.USD);
        fetchService.fetchAndPublishPrice(Coin.HBAR, Currency.USD);
        fetchService.fetchAndPublishPrice(Coin.LINK, Currency.USD);
        fetchService.fetchAndPublishPrice(Coin.RUNE, Currency.USD);
        logger.info("<----- scheduledPriceUpdate DONE");
    }

}
