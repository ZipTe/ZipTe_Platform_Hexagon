package com.zipte.platform.server.domain.user;

import com.zipte.platform.server.adapter.in.web.dto.request.UserConsentRequest;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor  // JPA 기본 생성자
@AllArgsConstructor
@Builder
public class UserConsent {

    private boolean personalInfoRequired;  // 필수 개인정보 동의
    private boolean termsRequired;         // 필수 이용약관 동의

    private boolean dataSharingOptional;   // 선택 개인정보 제공 동의
    private boolean adsOptional;           // 선택 광고 수신 동의
    private boolean marketingEmailsOptional; // 선택 이메일 마케팅 동의
    private boolean marketingSMSOptional;  // 선택 문자 마케팅 동의

    /// 정적 팩토리 메서드
    public static UserConsent of(boolean personal, boolean terms, boolean data, boolean ads, boolean marketingEmail, boolean marketingSMS) {
        return UserConsent.builder()
                .personalInfoRequired(personal)
                .termsRequired(terms)
                .dataSharingOptional(data)
                .adsOptional(ads)
                .marketingEmailsOptional(marketingEmail)
                .marketingSMSOptional(marketingSMS)
                .build();
    }

    public static UserConsent of(UserConsentRequest request) {

        /// 예외 처리
        if (!request.isPersonalInfoRequired()) {
            throw new IllegalArgumentException("개인정보 동의는 필수 입니다.");
        }

        if (!request.isTermsRequired()) {
            throw new IllegalArgumentException("이용약관 동의는 필수 입니다.");
        }

        return UserConsent.builder()
                .personalInfoRequired(request.isPersonalInfoRequired())
                .termsRequired(request.isTermsRequired())
                .dataSharingOptional(request.isDataSharingOptional())
                .adsOptional(request.isAdsOptional())
                .marketingEmailsOptional(request.isMarketingEmailsOptional())
                .marketingSMSOptional(request.isMarketingSMSOptional())
                .build();
    }

}
