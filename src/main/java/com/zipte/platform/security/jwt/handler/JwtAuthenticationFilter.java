package com.zipte.platform.security.jwt.handler;

import com.zipte.platform.core.response.ErrorCode;
import com.zipte.platform.security.jwt.exception.JwtAuthenticationException;
import com.zipte.platform.security.jwt.provider.JwtTokenProvider;
import com.zipte.platform.security.jwt.util.RequestMatcherHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final JwtAuthenticationFailureHandler failureHandler;
    private final RequestMatcherHolder requestMatcherHolder;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {

            /// 헤더에서 토큰 꺼내기
            String authorization = request.getHeader("Authorization");

            if (authorization != null && authorization.startsWith("Bearer ")) {
                String token = authorization.substring(7);

                /// 토큰 검증
                if (tokenProvider.validateToken(token)) {
                    var authentication = tokenProvider.getAuthentication(token);

                    /// 시큐리티 홀더에 해당 멤버 저장
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    filterChain.doFilter(request, response);
                } else {
                    throw new JwtAuthenticationException(ErrorCode.NOT_VALID.getMessage());
                }

            } else {
                throw new JwtAuthenticationException(ErrorCode.NO_TOKEN.getMessage());
            }
        }
        catch (JwtAuthenticationException ex) {
            // 실패 핸들러 호출
            failureHandler.commence(request, response, ex);
            return;
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        return requestMatcherHolder.getRequestMatchersByMinRole(null)
                .matches(request);
    }

}
