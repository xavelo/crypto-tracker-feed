package com.xavelo.crypto.fetcher.domain.model;

public enum Coin {

    AAVE("Aave"),
    ADA("Cardano"),
    APT("Aptos"),
    AVAX("Avalanche"),
    BNB("BNB"),
    BTC("Bitcoin"),
    DOT("DOT"),
    ETH("Ethereum"),
    FET("Fetch AI"),
    HBAR("Hedera Hashgraph"),
    ICP("Internet Computer"),
    KAS("Kaspa"),
    LINK("Chainlink"),
    LTC("Litecoin"),
    NEAR("NEAR Protocol"),
    OM("Mantra"),
    ONDO("Ondo"),
    RENDER("Render"),
    RUNE("Thorchain"),
    SOL("Solana"),
    SUI("SUI"),
    TRX("Tron"),
    UNI("Uniswap"),
    XLM("Stellar"),
    XRP("Ripple");

    private final String fullName;

    Coin(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}

