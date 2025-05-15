package com.zipte.platform.server.application.service;

import com.zipte.platform.security.jwt.service.JwtTokenService;
import com.zipte.platform.security.jwt.service.JwtTokenUseCase;
import com.zipte.platform.server.application.in.auth.LogoutUserUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LogoutService implements LogoutUserUseCase {

    /// 토큰 제공
    private final JwtTokenUseCase tokenService;

    /// 생성자
    public LogoutService(JwtTokenUseCase tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {

        // 쿠키 및 DB에서 리프레시 토큰을 삭제
        tokenService.deleteRefreshToken(request, response);

    }
}
