package com.xavelo.crypto.fetcher.infrstructure.in.cron;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xavelo.crypto.fetcher.application.PriceFetchException;
import com.xavelo.crypto.fetcher.domain.model.Coin;
import com.xavelo.crypto.fetcher.domain.model.Currency;
import com.xavelo.crypto.fetcher.domain.repository.DataService;
import com.xavelo.crypto.fetcher.domain.repository.FetchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduledDataFetcher {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledDataFetcher.class);

    private final DataService dataService;

    public ScheduledDataFetcher(DataService dataService) {
        this.dataService = dataService;
    }

    @Scheduled(fixedRateString = "${crypto.data-fetcher.interval}")
    public void scheduledPriceUpdate() throws PriceFetchException, JsonProcessingException {
        logger.info("");
        logger.info("-----> ScheduledDataFetcher STARTED");
        long startTime = System.currentTimeMillis(); // Start timer

        List<Coin> coins = List.of(
                Coin.BTC, Coin.ETH, Coin.ADA
        );

        dataService.getData(coins);

        long endTime = System.currentTimeMillis(); // End timer
        long duration = endTime - startTime;

        logger.info("<----- ScheduledDataFetcher DONE in {} ms", duration);
        logger.info("");
    }

}
