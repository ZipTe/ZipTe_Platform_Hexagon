package com.zipte.platform.server.adapter.in.web.dto.response;

import com.zipte.platform.core.util.DateFormatUtil;
import com.zipte.platform.server.domain.user.OAuthProvider;
import com.zipte.platform.server.domain.user.User;
import com.zipte.platform.server.domain.user.UserConsent;
import com.zipte.platform.server.domain.user.UserRole;
import lombok.Builder;
import java.util.List;

@Builder
public record UserMyInfoResponse(
        Long userId, String username, String nickname, String imageUrl,
        String email, String birthday, OAuthProvider social,
        List<UserRole> roles, UserConsentResponse consent,
        String createdAt
) {

    public static UserMyInfoResponse from(User user) {
        return UserMyInfoResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .imageUrl(user.getImageUrl())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .birthday(user.getBirthday())
                .social(user.getSocial())
                .roles(user.getRoles())
                .consent(UserConsentResponse.from(user.getConsent()))
                .createdAt(DateFormatUtil.formatPostDate(user.getCreatedAt()))
                .build();

    }


    /// 유저 광고 관련 Response
    @Builder
    public record UserConsentResponse(
            boolean personalInfoRequired,  // 필수 개인정보 동의
            boolean termsRequired,         // 필수 이용약관 동의

            boolean dataSharingOptional,   // 선택 개인정보 제공 동의
            boolean adsOptional,
            boolean marketingEmailsOptional,
            boolean marketingSMSOptional  // 선택 문자 마케팅 동의
    ) {
        public static UserConsentResponse from(UserConsent consent) {
            return UserConsentResponse.builder()
                    .personalInfoRequired(consent.isPersonalInfoRequired())
                    .termsRequired(consent.isTermsRequired())
                    .dataSharingOptional(consent.isDataSharingOptional())
                    .adsOptional(consent.isAdsOptional())
                    .marketingEmailsOptional(consent.isMarketingEmailsOptional())
                    .marketingSMSOptional(consent.isMarketingSMSOptional())
                    .build();
        }

    }
}

