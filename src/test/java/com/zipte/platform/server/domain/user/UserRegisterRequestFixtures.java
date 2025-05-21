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

    public static UserRegisterRequest badRequest() {
        return UserRegisterRequest.builder()
                .email("not-an-email") // 잘못된 이메일 형식
                .socialId(null) // nullable이라 괜찮음
                .username("") // 빈 문자열 (실제로는 null이 아니므로 @NotNull 통과할 수 있음, 필요 시 null로 테스트)
                .nickname("이것은20자를넘는아주아주긴닉네임입니다") // 20자 초과
                .provider(null) // nullable
                .imageUrl(null) // nullable
                .birthday(null) // @NotNull
                .consent(null) // @NotNull
                .build();
    }



}
