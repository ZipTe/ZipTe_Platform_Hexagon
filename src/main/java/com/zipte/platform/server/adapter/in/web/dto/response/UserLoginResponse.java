package com.zipte.platform.server.adapter.in.web.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
@Builder
@JsonPropertyOrder({"email", "accessToken"})
public class UserLoginResponse {

    private final String email;
    private final String accessToken;

    public static UserLoginResponse from(String email, String accessToken) {
        return UserLoginResponse.builder()
                .email(email)
                .accessToken(accessToken)
                .build();
    }

}
