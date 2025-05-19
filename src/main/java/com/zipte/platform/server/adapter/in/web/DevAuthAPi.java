package com.zipte.platform.server.adapter.in.web;

import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.security.jwt.provider.JwtTokenProvider;
import com.zipte.platform.security.oauth2.domain.PrincipalDetails;
import com.zipte.platform.server.adapter.in.web.dto.response.UserLoginResponse;
import com.zipte.platform.server.adapter.in.web.swagger.DevAuthAPiSpec;
import com.zipte.platform.server.application.out.user.UserPort;
import com.zipte.platform.server.domain.user.OAuthProvider;
import com.zipte.platform.server.domain.user.User;
import com.zipte.platform.server.domain.user.UserConsent;
import com.zipte.platform.server.domain.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Profile("dev")  // dev 환경에서만 활성화
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class DevAuthAPi implements DevAuthAPiSpec {

    private final UserPort userPort;
    private final JwtTokenProvider tokenProvider;

    @PostMapping("/dev-login-1")
    public ApiResponse<UserLoginResponse> devLogin1() {
        /// 테스트용 사용자 정보 생성
        User dev = createDev1();

        User user = userPort.saveUser(dev);

        /// PrincipalDetails 생성 (시스템에 따라 다름)
        PrincipalDetails principalDetails = PrincipalDetails.of(user);

        /// Authentication 객체 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principalDetails, null, principalDetails.getAuthorities()
        );

        /// SecurityContextHolder 에 인증 정보 등록
         SecurityContextHolder.getContext().setAuthentication(authentication);

        /// JWT 토큰 생성
        String token = tokenProvider.generateAccessToken(authentication);

        // 6. 응답 생성
        UserLoginResponse response = UserLoginResponse.from(user.getEmail(), token);

        return ApiResponse.ok(response);
    }

    @PostMapping("/dev-login-2")
    public ApiResponse<UserLoginResponse> devLogin2() {
        /// 테스트용 사용자 정보 생성
        User dev = createDev2();

        User user = userPort.saveUser(dev);

        /// PrincipalDetails 생성 (시스템에 따라 다름)
        PrincipalDetails principalDetails = PrincipalDetails.of(user);

        /// Authentication 객체 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principalDetails, null, principalDetails.getAuthorities()
        );

        /// SecurityContextHolder 에 인증 정보 등록
        SecurityContextHolder.getContext().setAuthentication(authentication);

        /// JWT 토큰 생성
        String token = tokenProvider.generateAccessToken(authentication);

        // 6. 응답 생성
        UserLoginResponse response = UserLoginResponse.from(user.getEmail(), token);

        return ApiResponse.ok(response);
    }

    /// 임시 유저 생성
    private User createDev1() {
        return User.builder()
                .id(1L)
                .socialId("dev-kakao-id")
                .email("zipte_kakao@example.com")
                .username("kakao개발자")
                .nickname("DevUserKakao")
                .birthday("1990-01-01")
                .imageUrl("https://example.com/image.png")
                .social(OAuthProvider.KAKAO) // 테스트용 값 (Enum)
                .consent(UserConsent.of(true, true, true, true, true, true)) // 테스트 동의 정보
                .roles(List.of(UserRole.ADMIN)) // 관리자 권한 부여
                .build();
    }

    private User createDev2() {
        return User.builder()
                .id(2L)
                .socialId("dev-naver-id")
                .email("zipte_naver@example.com")
                .username("naver개발자")
                .nickname("DevUserNaver")
                .birthday("1990-01-01")
                .imageUrl("https://example.com/image.png")
                .social(OAuthProvider.NAVER) // 테스트용 값 (Enum)
                .consent(UserConsent.of(true, true, true, true, true, true)) // 테스트 동의 정보
                .roles(List.of(UserRole.ADMIN)) // 관리자 권한 부여
                .build();
    }

}

