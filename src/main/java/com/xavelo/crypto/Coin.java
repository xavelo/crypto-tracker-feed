package com.xavelo.crypto;

public enum Coin {

    BTC("Bitcoin"),
    ETH("Ethereum"),
    HBAR("Hedera Hashgraph"),
    ADA("Cardano");

    private final String fullName;

    Coin(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}

