package com.zipte.platform.server.domain.user;

import com.zipte.platform.server.adapter.in.web.dto.request.UserRegisterRequest;

public class UserRegisterRequestFixtures {

    public static UserRegisterRequest stub() {
        return UserRegisterRequest.builder()
                .email("test@example.com")
                .socialId("social123")
                .username("홍길동")
                .nickname("길동이")
                .provider(OAuthProvider.KAKAO)
                .imageUrl("https://image.url/profile.png")
                .birthday("1990-01-01")
                .consent(UserConsentRequestFixtures.allAgreed())
                .build();
    }

    public static UserRegisterRequest stub_false() {
        return UserRegisterRequest.builder()
                .email("test@example.com")
                .socialId("social123")
                .username("홍길동")
                .nickname("길동이")
                .provider(OAuthProvider.KAKAO)
                .imageUrl("https://image.url/profile.png")
                .birthday("1990-01-01")
                .consent(UserConsentRequestFixtures.none())
                .build();
    }

    public static UserRegisterRequest stub_required() {
        return UserRegisterRequest.builder()
                .email("test@example.com")
                .socialId("social123")
                .username("홍길동")
                .nickname("길동이")
                .provider(OAuthProvider.KAKAO)
                .imageUrl("https://image.url/profile.png")
                .birthday("1990-01-01")
                .consent(UserConsentRequestFixtures.onlyRequired())
                .build();
    }

    public static UserRegisterRequest withoutConsent() {
        return UserRegisterRequest.builder()
                .email("test@example.com")
                .socialId("social123")
                .username("홍길동")
                .nickname("길동이")
                .provider(OAuthProvider.NAVER)
                .imageUrl("https://image.url/profile.png")
                .birthday("1990-01-01")
                .consent(null) // 일부러 동의 누락
                .build();
    }

}
