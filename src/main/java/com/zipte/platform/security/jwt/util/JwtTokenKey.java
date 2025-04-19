package com.zipte.platform.security.jwt.util;

public class JwtTokenKey {

    // AccessToken의 기본 만료 시간 (1시간)
    public static final long ACCESS_TOKEN_EXPIRATION_TIME = 60 * 60 * 1000L;  // 1시간 (3600초)

    // RefreshToken의 기본 만료 시간 (7일)
    public static final long REFRESH_TOKEN_EXPIRATION_TIME = 60L * 60 * 24 * 7 * 1000;  // 7일 (밀리초 단위)

}
