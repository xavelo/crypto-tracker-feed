package com.xavelo.crypto.data;

public enum Coin {

    BTC("bitcoin"),
    ETH("ethereum"),    
    XRP("Ripple"),
    LINK("Chainlink"),
    ADA("Cardano"),
    DOT("Chainlink"),
    HBAR("Hedera Hashgraph"),
    RUNE("Thorchain");

    private final String fullName;

    Coin(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}

