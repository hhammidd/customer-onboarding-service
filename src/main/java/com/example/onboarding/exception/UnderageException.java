package com.example.onboarding.exception;

public class UnderageException extends RuntimeException {
    public UnderageException(String message) {
        super(message);
    }
}