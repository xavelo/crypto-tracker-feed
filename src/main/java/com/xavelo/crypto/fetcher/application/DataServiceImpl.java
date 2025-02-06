package com.xavelo.crypto.fetcher.application;

import com.xavelo.crypto.fetcher.domain.model.Coin;
import com.xavelo.crypto.fetcher.domain.model.Data;
import com.xavelo.crypto.fetcher.domain.repository.DataService;
import com.xavelo.crypto.fetcher.infrstructure.out.external.CoinGeckoApiAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
public class DataServiceImpl implements DataService {

    private static final Logger logger = LoggerFactory.getLogger(DataServiceImpl.class); // Create logger instance

    private final CoinGeckoApiAdapter coinGeckoApiAdapter;

    @Autowired
    public DataServiceImpl(CoinGeckoApiAdapter coinGeckoApiAdapter) {
        this.coinGeckoApiAdapter = coinGeckoApiAdapter;
    }

    @Override
    public List<Data> getData(List<Coin> coins) {
        return coinGeckoApiAdapter.getData(coins);
    }

}
