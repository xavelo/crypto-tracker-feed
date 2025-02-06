package com.xavelo.crypto.fetcher.domain.repository;

import com.xavelo.crypto.fetcher.domain.model.Coin;
import com.xavelo.crypto.fetcher.domain.model.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DataService {

    public List<Data> getData(List<Coin> coins);

}
