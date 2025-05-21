package com.zipte.platform.server.adapter.in.web.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class UserUpdateRequest {

    private String nickname;

    private MultipartFile image;

    @Pattern(
            regexp = "^\\d{4}-\\d{2}-\\d{2}$",
            message = "생년월일은 yyyy-MM-dd 형식이어야 합니다."
    )
    private String birthday;

    private UserConsentUpdateRequest consent;

}


