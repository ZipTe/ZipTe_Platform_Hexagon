package com.zipte.platform.server.adapter.in.web.dto.response;

import com.zipte.platform.security.oauth2.domain.OAuth2UserInfo;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
public class OAuth2UserInfoResponse {

    private String provider;
    private String providerId;
    private String email;
    private String userName;
    private String imageUrl;

    public static OAuth2UserInfoResponse from(OAuth2UserInfo oAuth2UserInfo) {
        return OAuth2UserInfoResponse.builder()
                .provider(oAuth2UserInfo.getProvider())
                .providerId(oAuth2UserInfo.getProviderId())
                .email(oAuth2UserInfo.getEmail())
                .userName(oAuth2UserInfo.getUserName())
                .imageUrl(oAuth2UserInfo.getImageUrl())
                .build();

    }
}
