package com.xavelo.crypto.fetcher.domain.model;

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
    ICP("Internet Computer"),
    LTC("Litecoin"),
    UNI("Uniswap"),
    AAVE("Aave"),
    APT("Aptos"),
    ONDO("Ondo"),
    FET("Fetch AI"),
    HBAR("Hedera Hashgraph"),
    KAS("Kaspa"),
    LINK("Chainlink"),
    NEAR("NEAR Protocol"),
    OM("Mantra"),
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

