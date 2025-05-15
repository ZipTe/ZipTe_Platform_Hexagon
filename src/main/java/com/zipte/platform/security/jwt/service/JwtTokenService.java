package com.zipte.platform.security.jwt.service;

import com.zipte.platform.security.jwt.domain.JwtToken;
import com.zipte.platform.security.jwt.domain.JwtTokenRedisRepository;
import com.zipte.platform.security.jwt.provider.JwtTokenProvider;
import com.zipte.platform.security.oauth2.domain.PrincipalDetails;
import com.zipte.platform.server.application.out.user.UserPort;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.zipte.platform.security.jwt.util.JwtTokenKey.REFRESH_TOKEN_EXPIRATION_TIME;

@Service
@Transactional
@RequiredArgsConstructor
public class JwtTokenService implements JwtTokenUseCase {

    private final JwtTokenProvider provider;
    private final JwtTokenRedisRepository repository;

    // 유저 가져오기
    private final UserPort userPort;

    // 액세스 토큰 생성하기
    @Override
    public String createAccessToken(Authentication authentication) {
        return provider.generateAccessToken(authentication);
    }

    // 리프레쉬 토큰 생성하기
    @Override
    public void createRefreshToken(HttpServletResponse response, Authentication authentication) {

        // 토큰을 발급한다.
        String refreshToken = provider.generateRefreshToken(authentication);

        // 만료 시간 설정
        LocalDateTime plusSeconds = LocalDateTime.now().plusSeconds(REFRESH_TOKEN_EXPIRATION_TIME);
        Long expiredAt = plusSeconds.atZone(ZoneId.systemDefault()).toEpochSecond();

        /// 인증 객체에서 정보 가져오기
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        // 해당 토큰을 레디스에 저장한다.
        JwtToken jwtToken = JwtToken.of(principalDetails.getId(), refreshToken, expiredAt);
        repository.save(jwtToken);

        // 토큰을 쿠키에 저장한다.
        setRefreshTokenInCookie(response, refreshToken);
    }

    // 재발급 하기
    @Override
    public String reissueByRefreshToken(HttpServletRequest request, HttpServletResponse response) {

        // 쿠키에서 리프레쉬 토큰 가져오기
        String refreshToken = getRefreshTokenFromCookie(request)
                .orElseThrow(() -> new NoSuchElementException("쿠키에 RefreshToken 존재하지 않습니다."));

        // 쿠키 validate 진행한다.
        if (!provider.validateToken(refreshToken)) {
            throw new SecurityException("유효하지 않은 토큰입니다.");
        };

        // DB에 존재하는지 체크한다.
        JwtToken jwtToken = repository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new SecurityException("DB에 RefreshToken 존재하지 않습니다."));

        // 유저를 조회한다.
        Authentication authentication = provider.getAuthentication(jwtToken.getRefreshToken());

        return provider.generateAccessToken(authentication);
    }

    @Override
    public void deleteRefreshToken(HttpServletRequest request, HttpServletResponse response) {

        // 쿠키를 가져오기
        String refreshToken = getRefreshTokenFromCookie(request)
                .orElseThrow(() -> new SecurityException("쿠키에 RefreshToken 존재하지 않습니다."));

        // 쿠키를 null 설정
        deleteRefreshTokenFromCookies(response);

        // DB에서도 삭제
        repository.deleteByRefreshToken(refreshToken);
    }

    /// 내부 함수
    // 쿠키에 리프레쉬 토큰을 저장
    private void setRefreshTokenInCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge((int) REFRESH_TOKEN_EXPIRATION_TIME);
        response.addCookie(cookie);
    }


    // 쿠키에서 refreshToken 추출하는 메서드
    private Optional<String> getRefreshTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    return Optional.ofNullable(cookie.getValue());
                }
            }
        }
        return Optional.empty(); // Optional.empty() 반환
    }

    // 쿠키에서 리프레시 토큰 삭제
    private void deleteRefreshTokenFromCookies(HttpServletResponse response) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", null);
        refreshTokenCookie.setMaxAge(0); // 쿠키 만료 시간을 0으로 설정하여 삭제
        refreshTokenCookie.setPath("/"); // 쿠키의 경로를 지정
        response.addCookie(refreshTokenCookie); // 응답에 추가하여 클라이언트에게 전송
    }
}
