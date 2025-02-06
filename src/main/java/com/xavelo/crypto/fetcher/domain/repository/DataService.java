package com.xavelo.crypto.fetcher.domain.repository;

import com.xavelo.crypto.fetcher.domain.model.Coin;
import com.xavelo.crypto.fetcher.domain.model.CoinData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DataService {

    public List<CoinData> getData(List<Coin> coins);

}
