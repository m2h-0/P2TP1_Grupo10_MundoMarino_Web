package com.grupo10.mundomarino.exception;

public class EspecieNotFoundException extends ServiceException {
    public EspecieNotFoundException() { super(); }
    public EspecieNotFoundException(String message) { super(message); }
    public EspecieNotFoundException(Throwable cause) { super(cause); }
    public EspecieNotFoundException(String message, Throwable cause) { super(message, cause); }
}