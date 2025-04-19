package com.zipte.platform.server.domain.user;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {

    MEMBER("ROLE_MEMBER"),
    ADMIN("ROLE_ADMIN");

    private final String role;

    @JsonValue
    public String getRole() {
        return role;
    }
}
