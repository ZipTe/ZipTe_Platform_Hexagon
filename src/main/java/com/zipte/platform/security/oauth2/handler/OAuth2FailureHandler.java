package com.zipte.platform.security.oauth2.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zipte.platform.security.oauth2.handler.exception.RedirectToSignupException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;

@Log4j2
@Component
@RequiredArgsConstructor
public class OAuth2FailureHandler implements AuthenticationFailureHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        /// 최초 로그인한 유저의 경우
        if (exception instanceof RedirectToSignupException redirect) {

            /// 유저 정보 가져오기

            Map<String, Object> attributes = redirect.getOAuth2UserAttributes();
            String provider = redirect.getProvider();

            /// UUID 기반 state 생성
            String state = UUID.randomUUID().toString();

            /// provider 넣기
            Map<String, Object> stringObjectMap = new java.util.HashMap<>(attributes);
            stringObjectMap.put("provider", provider);

            /// 사용자 임시 정보 Redis에 저장 (5~10분 TTL 권장)
            redisTemplate.opsForValue().set("OAUTH2:TEMP:" + state, objectMapper.writeValueAsString(stringObjectMap), Duration.ofMinutes(5));

            /// 프론트에 state로 리다이렉트
            String redirectUrl = UriComponentsBuilder
                    .fromUriString("http://localhost:3000/signup")
                    .queryParam("state", state)
                    .build()
                    .toUriString();

            redirectStrategy.sendRedirect(request, response, redirectUrl);
        }
        else {
            response.sendRedirect("/login?error");
        }
    }
}
