package com.zipte.platform.security.oauth2.domain.naver;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zipte.platform.security.oauth2.domain.OAuth2UserInfo;

import java.util.Map;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverUserInfo implements OAuth2UserInfo {

    private final Map<String, Object> response;

    public NaverUserInfo(Map<String, Object> attributes) {
        this.response = (Map<String, Object>) attributes.get("response");
    }

    @Override
    public String getProviderId() {
        return Optional.ofNullable(response.get("id"))
                .map(Object::toString)
                .orElse("Unknown");
    }

    @Override
    public String getProvider() {
        return "NAVER";
    }

    @Override
    public String getEmail() {
        return Optional.ofNullable(response.get("email"))
                .map(Object::toString)
                .orElse("No email provided");
    }

    @Override
    public String getUserName() {
        return Optional.ofNullable(response.get("name"))
                .map(Object::toString)
                .orElse("No name provided");
    }

    @Override
    public String getImageUrl() {
        return Optional.ofNullable(response.get("profile_image"))
                .map(Object::toString)
                .orElse("No image provided");
    }

}
