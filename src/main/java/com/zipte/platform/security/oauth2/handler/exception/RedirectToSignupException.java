package com.zipte.platform.security.oauth2.handler.exception;

import org.springframework.security.core.AuthenticationException;

public class RedirectToSignupException extends AuthenticationException {
    public RedirectToSignupException(String message) {
        super(message);
    }
}
