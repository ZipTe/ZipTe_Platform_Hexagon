package com.zipte.platform.security.oauth2.handler.exception;

import com.zipte.platform.core.response.ErrorCode;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

import java.util.Map;

@Getter
public class RedirectToSignupException extends AuthenticationException {

    private final Map<String, Object> oAuth2UserAttributes;
    private final String provider;

    public RedirectToSignupException(Map<String, Object> oAuth2UserAttributes, String provider) {

        super(ErrorCode.FIRST_LOGIN.getMessage());
        this.oAuth2UserAttributes = oAuth2UserAttributes;
        this.provider = provider;
    }
}
