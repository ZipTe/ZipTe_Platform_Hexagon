package com.zipte.platform.server.domain.user;

import com.zipte.platform.server.adapter.in.web.dto.request.UserConsentRequest;

public class UserConsentRequestFixtures {

    public static UserConsentRequest allAgreed() {
        return UserConsentRequest.builder()
                .personalInfoRequired(true)
                .termsRequired(true)
                .dataSharingOptional(true)
                .adsOptional(true)
                .marketingEmailsOptional(true)
                .marketingSMSOptional(true)
                .build();
    }

    public static UserConsentRequest onlyRequired() {
        return UserConsentRequest.builder()
                .personalInfoRequired(true)
                .termsRequired(true)
                .dataSharingOptional(false)
                .adsOptional(false)
                .marketingEmailsOptional(false)
                .marketingSMSOptional(false)
                .build();
    }

    public static UserConsentRequest none() {
        return UserConsentRequest.builder()
                .personalInfoRequired(false)
                .termsRequired(false)
                .dataSharingOptional(false)
                .adsOptional(false)
                .marketingEmailsOptional(false)
                .marketingSMSOptional(false)
                .build();
    }
}
