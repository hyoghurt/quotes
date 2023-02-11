package com.example.kameleoon.exception;

public class QuoteNotFoundException extends RuntimeException {
    public QuoteNotFoundException() {
        super();
    }

    public QuoteNotFoundException(String msg) {
        super(msg);
    }
}
