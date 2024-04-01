package com.example.exception;

/**
 * Custom exception used in the the post register endpoint
 * represents when an account already exists
 */
public class AccountExistsException extends RuntimeException {
    public AccountExistsException(String errorMessage) {
        super(errorMessage);
    }
}
