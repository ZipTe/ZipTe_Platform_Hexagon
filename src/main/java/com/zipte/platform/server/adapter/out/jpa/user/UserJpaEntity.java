package com.zipte.platform.server.adapter.out.jpa.user;

import com.zipte.platform.server.adapter.out.jpa.BaseEntity;
import com.zipte.platform.server.domain.user.OAuthProvider;
import com.zipte.platform.server.domain.user.User;
import com.zipte.platform.server.domain.user.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UserJpaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String socialId;

    private String email;

    private String username;

    private String nickname;

    private String birthday;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private OAuthProvider social;

    @Embedded
    private UserConsentJpaEntity consent;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<UserRole> roles = new ArrayList<>();

    // from Domain
    public static UserJpaEntity from(User user) {
        return UserJpaEntity.builder()
                .socialId(user.getSocialId())
                .email(user.getEmail())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .imageUrl(user.getImageUrl())
                .social(user.getSocial())
                .birthday(user.getBirthday())
                .consent(UserConsentJpaEntity.from(user.getConsent()))
                .roles(user.getRoles())
                .build();
    }

    // to Domain
    public User toDomain() {
        return User.builder()
                .id(id)
                .email(email)
                .username(username)
                .nickname(nickname)
                .imageUrl(imageUrl)
                .socialId(socialId)
                .social(social)
                .birthday(birthday)
                .consent(consent.toDomain())
                .roles(roles)
                .createdAt(getCreatedAt())
                .build();
    }

    /// DB 수정용
    public void changeUser(User user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.imageUrl = user.getImageUrl();
        this.birthday = user.getBirthday();
        this.consent = UserConsentJpaEntity.from(user.getConsent());
        this.roles = user.getRoles();
    }

}

