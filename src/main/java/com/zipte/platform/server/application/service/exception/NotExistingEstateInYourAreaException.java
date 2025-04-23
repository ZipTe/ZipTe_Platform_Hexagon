package com.zipte.platform.server.application.service.exception;

public class NotExistingEstateInYourAreaException extends RuntimeException {

    public NotExistingEstateInYourAreaException(String message) {
        super(message);
    }
}
