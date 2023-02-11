package com.example.kameleoon.exception;

public class UserBadRequestException extends RuntimeException {
    public UserBadRequestException() {
        super();
    }

    public UserBadRequestException(String msg) {
        super(msg);
    }
}
