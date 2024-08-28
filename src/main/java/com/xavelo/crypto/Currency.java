package com.xavelo.crypto;

public enum Currency {

    USD("USD", "US Dollar"),
    EUR("EUR", "Euro");

    private final String symbol;
    private final String fullName;

    Currency(String symbol, String fullName) {
        this.symbol = symbol;
        this.fullName = fullName;
    }

    public String getSymbol() {
        return symbol;
    }
    public String getFullName() {
        return fullName;
    }

}
