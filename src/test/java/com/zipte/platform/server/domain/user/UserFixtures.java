package com.zipte.platform.server.domain.user;

import java.util.List;

public class UserFixtures {

    public static User stub() {
        return User.builder()
                .id(1L)
                .socialId("social-123")
                .email("user@example.com")
                .username("username1")
                .nickname("nickname1")
                .birthday("1990-01-01")
                .imageUrl("https://example.com/profile.jpg")
                .social(OAuthProvider.KAKAO)
                .consent(UserConsentFixtures.stub())
                .roles(List.of(UserRole.MEMBER))
                .build();
    }

    public static User stub(Long id) {
        return User.builder()
                .id(id)
                .socialId("social-" + id)
                .email("user" + id + "@example.com")
                .username("username" + id)
                .nickname("nickname" + id)
                .birthday("1990-01-0" + (id % 10 + 1))
                .imageUrl("https://example.com/profile.jpg")
                .social(OAuthProvider.KAKAO)
                .consent(UserConsentFixtures.stub())
                .roles(List.of(UserRole.MEMBER))
                .build();
    }

    public static User stub(Long id, UserConsent consent) {
        return User.builder()
                .id(id)
                .socialId("social-" + id)
                .email("user" + id + "@example.com")
                .username("username" + id)
                .nickname("nickname" + id)
                .birthday("1990-01-0" + (id % 10 + 1))
                .imageUrl("https://example.com/profile.jpg")
                .social(OAuthProvider.KAKAO)
                .consent(consent)
                .roles(List.of(UserRole.MEMBER))
                .build();
    }
}
