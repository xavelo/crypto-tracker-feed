package com.xavelo.crypto.data;

public enum Coin {

    BTC("bitcoin"),
    ETH("ethereum"),    
    ADA("Cardano"),
    SOL("Solana"),
    BNB("BNB"),
    DOT("Chainlink"),
    AVAX("Avalanche"),
    XLM("Stellar"),
    SUI("SUI"),
    LTC("Litecoin"),
    FET("Fetch AI"),
    HBAR("Hedera Hashgraph"),
    KAS("Kaspa"),
    LINK("Chainlink"),
    NEAR("NEAR Protocol"),
    RENDER("Render"),
    RUNE("Thorchain"),
    TRX("Tron"),
    XRP("Ripple");

    private final String fullName;

    Coin(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}

