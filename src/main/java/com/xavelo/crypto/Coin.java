package com.xavelo.crypto;

public enum Coin {

    BTC("bitcoin"),
    ETH("ethereum"),
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

