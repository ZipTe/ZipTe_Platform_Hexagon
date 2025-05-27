package com.zipte.platform.server.adapter.in.web.swagger;


import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.security.oauth2.domain.PrincipalDetails;
import com.zipte.platform.server.adapter.in.web.dto.request.UserUpdateRequest;
import com.zipte.platform.server.adapter.in.web.dto.response.UserMyInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;

@Tag(name = "유저 API", description = "유저 정보 관련 API")
public interface UserApiSpec {

    @Operation(
            summary = "유저 정보 조회",
            description = "JWT 토큰을 기반으로 유저의 정보를 조회합니다"
    )
    ApiResponse<UserMyInfoResponse> getMyInfo(
            @AuthenticationPrincipal PrincipalDetails principalDetails);


    @Operation(
            summary = "유저 정보 수정",
            description = "JWT 토큰을 기반으로 유저의 정보를 수정합니다.",
            security = @SecurityRequirement(name = "JWT")
    )
    ApiResponse<String> updateMyInfo(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @ParameterObject @ModelAttribute UserUpdateRequest request);

}
