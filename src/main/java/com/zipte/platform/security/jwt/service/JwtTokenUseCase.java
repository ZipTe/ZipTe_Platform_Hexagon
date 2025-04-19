package com.zipte.platform.security.jwt.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

public interface JwtTokenUseCase {

    // 액세스 토큰 생성하기
    String createAccessToken(Authentication authentication);

    // 리프레쉬 토큰 생성하기
    void createRefreshToken(HttpServletResponse response, Authentication authentication);

    // 재발급 하기
    String reissueByRefreshToken(HttpServletRequest request, HttpServletResponse response);

    // Refresh 토큰 제거하기
    void deleteRefreshToken(HttpServletRequest request, HttpServletResponse response);
}
