package com.zipte.platform.server.adapter.in.web.swagger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.server.adapter.in.web.dto.request.UserRegisterRequest;
import com.zipte.platform.server.adapter.in.web.dto.response.OAuth2UserInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "유저 인증/인가 API", description = "유저 로그인/회원가입 관련 API")
public interface AuthAPiSpec {

    @Operation(
            summary = "유저 최초 회원가입",
            description = "소셜 로그인에서 처음 로그인 유저의 추가정보를 기입하여 회원가입합니다.",
            security = @SecurityRequirement(name = "JWT"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(name = "회원가입 가입 성공 예시", value = SUCCESS_PAYLOAD),
                                    @ExampleObject(name = "회원가입 가입 실패 예시", value = FAIL_PAYLOAD)
                            }
                    )
            )
    )
    ApiResponse<String> registerUser(
            @RequestBody @Valid UserRegisterRequest request);


    @Operation(
            summary = "최초 회원가입 유저 조회",
            description = "세션을 통해 최초 회원가입 유저의 소셜로그인 정보를 조회합니다."
    )
    ApiResponse<OAuth2UserInfoResponse> getTempUser(

            @PathVariable String state) throws JsonProcessingException;

    @Operation(
            summary = "Jwt 토큰 재발급",
            description = "쿠키를 통해 accessToken을 재발급 요청합니다."
    )
    ApiResponse<String> reissue(HttpServletRequest request, HttpServletResponse response);


    String SUCCESS_PAYLOAD = """
            {
               "email": "example@email.com",
               "socialId": "1234567890abcdef",
               "username": "홍길동",
               "nickname": "길동이짱",
               "provider": "KAKAO",
               "imageUrl": "https://example.com/profile.jpg",
               "birthday": "1990-01-01",
               "consent": {
                 "personalInfoRequired": true,
                 "termsRequired": true,
                 "dataSharingOptional": false,
                 "adsOptional": false,
                 "marketingEmailsOptional": false,
                 "marketingSMSOptional": false
               }
             }
            """;


    String FAIL_PAYLOAD = """
            {
               "email": "example@email.com",
               "socialId": "1234567890abcdef",
               "username": "홍길동",
               "nickname": "길동이짱",
               "provider": "KAKAO",
               "imageUrl": "https://example.com/profile.jpg",
               "birthday": "1990-01-01",
               "consent": {
                 "personalInfoRequired": false,
                 "termsRequired": false,
                 "dataSharingOptional": true,
                 "adsOptional": true,
                 "marketingEmailsOptional": true,
                 "marketingSMSOptional": true
               }
             }
            """;


}
