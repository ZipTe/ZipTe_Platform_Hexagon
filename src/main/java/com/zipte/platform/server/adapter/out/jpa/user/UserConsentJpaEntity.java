package com.zipte.platform.server.adapter.out.jpa.user;

import com.zipte.platform.server.domain.user.UserConsent;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter

public class UserConsentJpaEntity {

    private boolean personalInfoRequired;  // 필수 개인정보 동의
    private boolean termsRequired;         // 필수 이용약관 동의

    private boolean dataSharingOptional;   // 선택 개인정보 제공 동의
    private boolean adsOptional;           // 선택 광고 수신 동의
    private boolean marketingEmailsOptional; // 선택 이메일 마케팅 동의
    private boolean marketingSMSOptional;  // 선택 문자 마케팅 동의

    /// 정적 팩토리 메서드
    public static UserConsentJpaEntity from(UserConsent userConsent) {
        return UserConsentJpaEntity.builder()
                .personalInfoRequired(userConsent.isPersonalInfoRequired())
                .termsRequired(userConsent.isTermsRequired())
                .dataSharingOptional(userConsent.isDataSharingOptional())
                .adsOptional(userConsent.isAdsOptional())
                .marketingEmailsOptional(userConsent.isMarketingEmailsOptional())
                .marketingSMSOptional(userConsent.isMarketingSMSOptional())
                .build();
    }

    /// 도메인
    public UserConsent toDomain() {
        return UserConsent.builder()
                .personalInfoRequired(personalInfoRequired)
                .termsRequired(termsRequired)
                .dataSharingOptional(dataSharingOptional)
                .adsOptional(adsOptional)
                .marketingEmailsOptional(marketingEmailsOptional)
                .marketingSMSOptional(marketingSMSOptional)
                .build();
    }


}
