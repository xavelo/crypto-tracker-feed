package com.xavelo.crypto.service;

import com.xavelo.crypto.Coin;
import com.xavelo.crypto.Price;

public interface FetcherService {

    public Price fetchPrice(Coin coin);

}
