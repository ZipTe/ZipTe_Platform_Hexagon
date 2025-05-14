package com.zipte.platform.server.adapter.in.web.user;

import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.security.jwt.service.JwtTokenUseCase;
import com.zipte.platform.security.oauth2.domain.OAuth2UserInfo;
import com.zipte.platform.server.adapter.in.web.dto.request.UserRegisterRequest;
import com.zipte.platform.server.adapter.in.web.dto.response.OAuth2UserInfoResponse;
import com.zipte.platform.server.adapter.in.web.swagger.AuthAPiSpec;
import com.zipte.platform.server.application.in.auth.AuthUserUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/oauth2")
@RequiredArgsConstructor
public class AuthController implements AuthAPiSpec {

    // 토큰 재발급
    private final AuthUserUseCase authService;
    private final JwtTokenUseCase tokenService;

    @GetMapping("/session-user")
    public ApiResponse<OAuth2UserInfoResponse> getSessionUser(HttpSession session) {

        OAuth2UserInfo userInfo = (OAuth2UserInfo) session.getAttribute("user");
        OAuth2UserInfoResponse response = OAuth2UserInfoResponse.from(userInfo);
        log.info("로그 {}", response.toString());
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
