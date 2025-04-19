package com.zipte.platform.server.application.in.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface LogoutUserUseCase {

    /*
        사용자는 로그아웃을 진행할 수 있습니다.
        로그아웃을 진행하게된다면, RefreshToken 이 쿠키와 캐시에서 삭제됩니다.
     */

    // 로그아웃
    void logout(HttpServletRequest req, HttpServletResponse resp);
}
