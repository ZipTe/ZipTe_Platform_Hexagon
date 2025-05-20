package com.zipte.platform.server.adapter.in.web.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserConsentRequest {

    private boolean personalInfoRequired;  // 필수 개인정보 동의
    private boolean termsRequired;         // 필수 이용약관 동의

    private boolean dataSharingOptional;   // 선택 개인정보 제공 동의
    private boolean adsOptional;           // 선택 광고 수신 동의
    private boolean marketingEmailsOptional; // 선택 이메일 마케팅 동의
    private boolean marketingSMSOptional; // 선택 SMS 마케팅 동의
}
