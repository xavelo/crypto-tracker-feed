package com.xavelo.crypto.service;

import com.xavelo.crypto.Coin;
import com.xavelo.crypto.Price;

public interface FetchService {

    public Price fetchPrice(Coin coin);

}
