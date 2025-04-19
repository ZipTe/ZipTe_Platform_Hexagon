package com.zipte.platform.server.domain.user;

import com.zipte.platform.server.domain.BaseDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class User extends BaseDomain {

    private Long id;

    private String socialId;

    // 이메일은 중복 허용
    private String email;

    private String username;

    private String nickname;

    private String birthday;

    private String imageUrl;

    private OAuthProvider social;

    private UserConsent consent;

    private List<UserRole> roles;


    /// 정적 팩토리 메소드 생성자
    public static User of(String socialId, String email, String username, String nickname, String imageUrl, String birthday, OAuthProvider social, UserConsent consent) {

        List<UserRole> roles = new ArrayList<>();
        UserRole role = UserRole.MEMBER;
        roles.add(role);

        return User.builder()
                .socialId(socialId)
                .email(email)
                .username(username)
                .nickname(nickname)
                .imageUrl(imageUrl)
                .social(social)
                .birthday(birthday)
                .consent(consent)
                .roles(roles)
                .build();
    }

    /// 비즈니스 로직

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changeImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


}

