package com.xavelo.crypto.fetcher.application;

import com.xavelo.crypto.fetcher.domain.model.Coin;
import com.xavelo.crypto.fetcher.domain.model.CoinData;
import com.xavelo.crypto.fetcher.domain.repository.DataService;
import com.xavelo.crypto.fetcher.infrstructure.out.external.CoinGeckoApiAdapter;
import lombok.AllArgsConstructor;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Primary
@AllArgsConstructor
public class DataServiceImpl implements DataService {    

    private final CoinGeckoApiAdapter coinGeckoApiAdapter;

    @Override
    public List<CoinData> getData(List<Coin> coins) {
        return coinGeckoApiAdapter.getData(coins);
    }

}
