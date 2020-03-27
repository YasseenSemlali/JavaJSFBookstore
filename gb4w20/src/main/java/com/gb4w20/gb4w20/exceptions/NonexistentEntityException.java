package com.gb4w20.gb4w20.exceptions;

public class NonexistentEntityException extends Exception {

    public NonexistentEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonexistentEntityException(String message) {
        super(message);
    }
}
