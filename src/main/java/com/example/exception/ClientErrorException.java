package com.example.exception;

/**
 * An Exception class that represents the 400 bad request status code
 * Used for malformed input from user input
 */
public class ClientErrorException extends RuntimeException{
    public ClientErrorException(String errorMessage) {
        super(errorMessage);
    }
}
