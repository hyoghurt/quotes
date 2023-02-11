package com.example.kameleoon.exception;

public class QuoteForbiddenException extends RuntimeException {
    public QuoteForbiddenException() {
        super();
    }

    public QuoteForbiddenException(String msg) {
        super(msg);
    }
}
