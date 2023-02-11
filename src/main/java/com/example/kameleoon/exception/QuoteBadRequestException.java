package com.example.kameleoon.exception;

public class QuoteBadRequestException extends RuntimeException {
    public QuoteBadRequestException() {
        super();
    }

    public QuoteBadRequestException(String msg) {
        super(msg);
    }
}
