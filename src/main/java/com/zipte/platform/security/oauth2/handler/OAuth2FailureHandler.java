package com.zipte.platform.security.oauth2.handler;

import com.zipte.platform.security.oauth2.handler.exception.RedirectToSignupException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Log4j2
@Component
public class OAuth2FailureHandler implements AuthenticationFailureHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        /// 최초 로그인한 유저의 경우
        if (exception instanceof RedirectToSignupException) {

            // 프런트의 회원가입 페이지로 리디렉션
            redirectStrategy.sendRedirect(request, response, "http://localhost:3000/signup");
        }
        else {
            response.sendRedirect("/login?error");
        }
    }
}
