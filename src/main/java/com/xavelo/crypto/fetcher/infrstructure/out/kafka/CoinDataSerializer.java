package com.xavelo.crypto.fetcher.infrstructure.out.kafka;

import com.xavelo.crypto.fetcher.domain.model.CoinData;

public class CoinDataSerializer {

    public static String serializeToJson(CoinData coinData) {
        return coinData.toString();
    }

}
