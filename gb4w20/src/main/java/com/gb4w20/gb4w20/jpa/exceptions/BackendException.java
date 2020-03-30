package com.gb4w20.gb4w20.jpa.exceptions;

/**
 * Custom exception for backend problems
 * 
 * @author jeanrobatto
 */
public class BackendException extends Exception {

    public BackendException(String message) {
        super(message);
    }
}
