package com.pi.kursy.shared;

public abstract class GenericException extends Exception {

    protected GenericException(String message) {
        super(message);
    }

    public abstract String getStatus();
}
