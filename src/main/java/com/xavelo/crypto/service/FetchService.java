package com.xavelo.crypto.service;

import com.xavelo.crypto.Coin;
import com.xavelo.crypto.Price;
import org.springframework.stereotype.Service;

@Service
public interface FetchService {

    public Price fetchPrice(Coin coin);

}
