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

import java.util.List;

@Service
public class ScheduledPriceUpdater {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledPriceUpdater.class);

    private final FetchService fetchService;    

    public ScheduledPriceUpdater(FetchService fetchService) {
        this.fetchService = fetchService;
    }

    @Scheduled(fixedRateString = "${crypto.fetcher.interval}")
    public void scheduledPriceUpdate() throws PriceFetchException, JsonProcessingException {
        logger.info("");
        logger.info("-----> scheduledPriceUpdate STARTED");
        long startTime = System.currentTimeMillis(); // Start timer

        List<Coin> coins = List.of(
                Coin.BTC, Coin.ETH, Coin.SOL, Coin.ADA, Coin.BNB, Coin.DOT, Coin.AVAX,
                Coin.XLM, Coin.SUI, Coin.ICP, Coin.LTC, Coin.UNI, Coin.AAVE, Coin.APT,
                Coin.ONDO, Coin.FET, Coin.HBAR, Coin.KAS, Coin.LINK, Coin.NEAR,
                Coin.RENDER, Coin.RUNE, Coin.TRX, Coin.XRP, Coin.OM
        );

        for (Coin coin : coins) {
            fetchService.fetchAndPublishPrice(coin, Currency.USD);
        }

        long endTime = System.currentTimeMillis(); // End timer
        long duration = endTime - startTime;

        logger.info("<----- scheduledPriceUpdate DONE in {} ms", duration);
        logger.info("");
    }

}
