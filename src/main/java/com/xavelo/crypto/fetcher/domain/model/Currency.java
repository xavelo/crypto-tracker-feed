package com.xavelo.crypto.fetcher.domain.model;

public enum Currency {

    USD("US Dollar"),
    EUR("Euro");

    private final String fullName;

    Currency(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

}
