package com.xavelo.crypto.data;

public enum Coin {

    BTC("bitcoin"),
    ETH("ethereum"),
    HBAR("Hedera Hashgraph"),
    XRP("Ripple"),
    ADA("Cardano");

    private final String fullName;

    Coin(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}

