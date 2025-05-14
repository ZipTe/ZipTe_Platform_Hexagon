package com.zipte.platform.server.adapter.in.web.swagger;


import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.security.oauth2.domain.PrincipalDetails;
import com.zipte.platform.server.adapter.in.web.dto.request.UserUpdateRequest;
import com.zipte.platform.server.adapter.in.web.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "유저 API", description = "유저 정보 관련 API")
public interface UserApiSpec {

    @Operation(
            summary = "유저 정보 조회",
            description = "JWT 토큰을 기반으로 유저의 정보를 조회합니다"
    )
    ApiResponse<UserResponse> getMyInfo(
            @AuthenticationPrincipal PrincipalDetails principalDetails);


    @Operation(
            summary = "유저 정보 수정",
            description = "JWT 토큰을 기반으로 유저의 정보를 수정합니다.",
            security = @SecurityRequirement(name = "JWT"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(name = "유저 정보 수정 예시", value = SUCCESS_PAYLOAD),
                            }
                    )
            )
    )
    ApiResponse<String> updateMyInfo(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody UserUpdateRequest request);


    String SUCCESS_PAYLOAD = """
            {
              "nickname": "사용자닉네임",
              "password": "비밀번호123!",
              "passwordCheck": "비밀번호123!",
              "imageUrl": "https://example.com/profile.jpg",
              "description": "자기소개 또는 상태메시지",
              "birthday": "1990-01-01"
            }
            """;


}
