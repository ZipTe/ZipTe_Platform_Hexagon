package com.zipte.platform.server.adapter.in.web.swagger;

import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.server.adapter.in.web.dto.response.UserLoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "개발용 JWT 발급 API", description = "개발용 JWT 발급 API입니다.")
public interface DevAuthAPiSpec {
    @Operation(
            summary = "개발용 JWT를 발급 받을 토큰입니다."
    )
    ApiResponse<UserLoginResponse> devLogin();



}
