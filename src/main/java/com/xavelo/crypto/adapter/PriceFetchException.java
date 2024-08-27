package com.xavelo.crypto.adapter;

public class PriceFetchException extends Exception {

    public PriceFetchException(String message) {
        super(message);
    }

    public PriceFetchException(String message, Throwable cause) {
        super(message, cause);
    }

    public PriceFetchException(Throwable cause) {
        super(cause);
    }

    public PriceFetchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
