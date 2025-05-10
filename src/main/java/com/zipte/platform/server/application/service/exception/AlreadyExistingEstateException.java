package com.zipte.platform.server.application.service.exception;

public class AlreadyExistingEstateException extends RuntimeException {
    public AlreadyExistingEstateException(String message) {
        super(message);
    }
}
