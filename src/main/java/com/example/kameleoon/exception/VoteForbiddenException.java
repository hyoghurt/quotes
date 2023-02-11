package com.example.kameleoon.exception;

public class VoteForbiddenException extends RuntimeException {
    public VoteForbiddenException() {
        super();
    }

    public VoteForbiddenException(String msg) {
        super(msg);
    }
}
