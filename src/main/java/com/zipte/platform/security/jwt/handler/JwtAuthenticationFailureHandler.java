package com.zipte.platform.security.jwt.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.core.response.CustomException;
import com.zipte.platform.core.response.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFailureHandler implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        /// 401 에러
        CustomException exception = new CustomException(ErrorCode.SC_UNAUTHORIZED);
        ApiResponse<Object> apiResponse = ApiResponse.fail(exception);

        // 응답 설정
        response.setStatus(apiResponse.httpStatus().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Jackson으로 ApiResponse를 JSON으로 직렬화
        objectMapper.writeValue(response.getWriter(), apiResponse);
    }
}
