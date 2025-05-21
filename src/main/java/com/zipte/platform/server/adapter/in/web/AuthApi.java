package com.zipte.platform.server.adapter.in.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.core.util.RedisKeyGenerator;
import com.zipte.platform.security.jwt.service.JwtTokenUseCase;
import com.zipte.platform.security.oauth2.domain.OAuth2UserInfo;
import com.zipte.platform.security.oauth2.util.OAuth2UserInfoFactory;
import com.zipte.platform.server.adapter.in.web.dto.request.UserRegisterRequest;
import com.zipte.platform.server.adapter.in.web.dto.response.OAuth2UserInfoResponse;
import com.zipte.platform.server.adapter.in.web.swagger.AuthAPiSpec;
import com.zipte.platform.server.application.in.auth.AuthUserUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/oauth2")
@RequiredArgsConstructor
public class AuthApi implements AuthAPiSpec {

    // 토큰 재발급
    private final AuthUserUseCase authService;
    private final JwtTokenUseCase tokenService;
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;


    @GetMapping("/temp-user/{state}")
    public ApiResponse<OAuth2UserInfoResponse> getTempUser(@PathVariable String state) throws JsonProcessingException {

        /// 레디스 Key
        String key = RedisKeyGenerator.getTempUser(state);

        /// 값 읽기
        String json = redisTemplate.opsForValue().get(key);

        Map<String, Object> attributes = objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});
        String provider = (String) attributes.get("provider");

        // Map을 넘겨서 OAuth2UserInfo 생성
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.create(provider, attributes);

        OAuth2UserInfoResponse response = OAuth2UserInfoResponse.from(userInfo);

        log.info("로그 {}", response);

        return ApiResponse.ok(response);
    }


    // 최초 로그인 시, 유저의 추가 정보 기입
    @PostMapping()
    public ApiResponse<String> registerUser(
            @RequestBody @Valid UserRegisterRequest request) {

        authService.registerUser(request);

        return ApiResponse.ok("가입이 완료되엇습니다.");
    }

    // 토큰 재발급
    @PostMapping("/reissue")
    public ApiResponse<String> reissue(HttpServletRequest request, HttpServletResponse response) {
        String newAccessToken = tokenService.reissueByRefreshToken(request, response);

        return ApiResponse.ok(newAccessToken);
    }
}
