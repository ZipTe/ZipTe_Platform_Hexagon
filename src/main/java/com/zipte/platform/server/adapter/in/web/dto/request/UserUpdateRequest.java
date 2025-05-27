package com.zipte.platform.server.adapter.in.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class UserUpdateRequest {

    @Schema(description = "사용자 이름", example = "홍길동")
    private String nickname;

    @Schema(description = "프로필 사진")
    private MultipartFile image;

    @Pattern(
            regexp = "^\\d{4}-\\d{2}-\\d{2}$",
            message = "생년월일은 yyyy-MM-dd 형식이어야 합니다."
    )
    @Schema(description = "생년월일", example = "2000-01-01")
    private String birthday;

    @Schema(description = "사용자 동의 여부")
    private UserConsentUpdateRequest consent;

}


