package com.xavelo.crypto.fetcher.infrastructure.in.cron;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xavelo.crypto.fetcher.application.PriceFetchException;
import com.xavelo.crypto.fetcher.domain.model.Coin;
import com.xavelo.crypto.fetcher.domain.model.CoinData;
import com.xavelo.crypto.fetcher.domain.repository.DataService;
import com.xavelo.crypto.fetcher.domain.repository.PublishService;

import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ScheduledDataFetcher {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledDataFetcher.class);

    private final DataService dataService;
    private final PublishService publishService;

    @Scheduled(fixedRateString = "${crypto.data-fetcher.interval}")
    public void scheduledDataFetch() throws PriceFetchException, JsonProcessingException {
        logger.info("");
        logger.info("-----> ScheduledDataFetcher STARTED");
        long startTime = System.currentTimeMillis(); // Start timer

        List<Coin> coins = List.of(
                Coin.BTC, Coin.ETH, Coin.ADA, Coin.SOL, Coin.BNB, Coin.DOT, Coin.LINK
        );

        for (CoinData coinData : dataService.getData(coins)) {
            logger.debug("publishing coin data for {}...", coinData.getSymbol());
            publishService.publishCoinData(coinData);
        }

        long endTime = System.currentTimeMillis(); // End timer
        long duration = endTime - startTime;

        logger.info("<----- ScheduledDataFetcher DONE in {} ms", duration);
        logger.info("");
    }

}
