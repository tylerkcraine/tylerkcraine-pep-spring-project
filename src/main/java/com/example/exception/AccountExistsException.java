package com.example.exception;

public class AccountExistsException extends RuntimeException {
    public AccountExistsException(String errorMessage) {
        super(errorMessage);
    }
}
