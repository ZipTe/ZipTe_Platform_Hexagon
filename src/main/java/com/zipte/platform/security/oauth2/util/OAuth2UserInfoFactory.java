package com.zipte.platform.security.oauth2.util;

import com.zipte.platform.security.oauth2.domain.OAuth2UserInfo;
import com.zipte.platform.security.oauth2.domain.google.GoogleUserInfo;
import com.zipte.platform.security.oauth2.domain.kakao.KakaoUserInfo;
import com.zipte.platform.security.oauth2.domain.naver.NaverUserInfo;

import java.util.Map;


public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo create(String registrationId, Map<String, Object> attributes) {
        OAuth2UserInfo userInfo;

        switch (registrationId.toUpperCase()) {
            case "KAKAO":
                userInfo = new KakaoUserInfo(attributes);
                break;
            case "NAVER":
                userInfo = new NaverUserInfo(attributes);
                break;
            case "GOOGLE":
                userInfo = new GoogleUserInfo(attributes);
                break;
            default:
                throw new IllegalArgumentException("Unknown provider: " + registrationId);
        }
        return userInfo;
    }

}
