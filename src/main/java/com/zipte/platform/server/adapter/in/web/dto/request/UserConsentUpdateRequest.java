package com.zipte.platform.server.adapter.in.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserConsentUpdateRequest {

    @Schema(description = "개인정보 제공 동의", example = "true")
    private boolean dataSharingOptional;   // 선택 개인정보 제공 동의

    @Schema(description = "광고 수신 동의", example = "true")
    private boolean adsOptional;           // 선택 광고 수신 동의

    @Schema(description = "이메일 마케팅 동의", example = "true")
    private boolean marketingEmailsOptional; // 선택 이메일 마케팅 동의

    @Schema(description = "SMS 마케팅 동의", example = "true")
    private boolean marketingSMSOptional; // 선택 SMS 마케팅 동의

}
