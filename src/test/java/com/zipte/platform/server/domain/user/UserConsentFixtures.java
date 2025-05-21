package com.zipte.platform.server.domain.user;

public class UserConsentFixtures {

    public static UserConsent stub() {
        return UserConsent.of(
                true,  // personalInfoRequired
                true,  // termsRequired
                true,  // dataSharingOptional
                false, // adsOptional
                true,  // marketingEmailsOptional
                false  // marketingSMSOptional
        );
    }

    public static UserConsent requiredOnly() {
        return UserConsent.of(
                true,
                true,
                false,
                false,
                false,
                false
        );
    }

    public static UserConsent requiredFalse() {
        return UserConsent.of(
                false,
                false,
                true,
                true,
                true,
                true
        );
    }

    public static UserConsent allAgreed() {
        return UserConsent.of(
                true,
                true,
                true,
                true,
                true,
                true
        );
    }
}
