package com.grupo10.mundomarino.exception;

public class ContactoException extends RuntimeException {

    public ContactoException(String message) {
        super(message);
    }

    public ContactoException(String message, Throwable cause) {
        super(message, cause);
    }
}