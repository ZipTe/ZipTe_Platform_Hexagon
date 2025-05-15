package com.zipte.platform.security.jwt.util;

import com.zipte.platform.server.domain.user.UserRole;
import io.micrometer.common.lang.Nullable;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.http.HttpMethod.*;

@Component
public class RequestMatcherHolder {

    private static final List<RequestInfo> REQUEST_INFO_LIST = List.of(

            // 공통
            new RequestInfo(OPTIONS, "/**", null),
            new RequestInfo(GET, "/", null),

            // 개발용
            new RequestInfo(POST, "/api/v1/auth/dev-login", null),

            // auth
            new RequestInfo(POST, "/api/v1/oauth2", null),
            new RequestInfo(GET, "/api/v1/oauth2/temp-user/**", null),
            new RequestInfo(GET, "/api/v1/oauth2/reissue", null),

            // user
            new RequestInfo(POST, "/api/v1/reissue/", UserRole.MEMBER),
            new RequestInfo(GET, "/api/v1/users/**", UserRole.MEMBER),

            // admin
            new RequestInfo(GET, "/api/member/**", UserRole.MEMBER),
            new RequestInfo(POST, "/api/admin/**", UserRole.ADMIN),

            // estate
            new RequestInfo(GET, "/api/v1/estate/**", null),
            new RequestInfo(GET, "/api/v1/estate/price/**", null),

            // 지역
            new RequestInfo(GET, "/api/v1/region/**", null),
            new RequestInfo(GET, "/api/v1/region/price/**", null),
            new RequestInfo(POST, "/api/v1/region/price/**", UserRole.MEMBER),

            // 매물
            new RequestInfo(POST, "/api/v1/property/**", null),
            new RequestInfo(GET, "/api/v1/property/**", null),
            new RequestInfo(DELETE, "/api/v1/property/**", null),

            // 거주지
            new RequestInfo(GET, "/api/v1/ownership/**", UserRole.MEMBER),

            // 관심목록
            new RequestInfo(GET, "/api/v1/favorite/**", UserRole.MEMBER),
            new RequestInfo(POST, "/api/v1/favorite/**", UserRole.MEMBER),
            new RequestInfo(DELETE, "/api/v1/favorite/**", UserRole.MEMBER),

            // 알림
            new RequestInfo(GET, "/api/v1/notification/**", UserRole.MEMBER),

            // review
            new RequestInfo(POST, "/api/v1/review/**", UserRole.MEMBER),
            new RequestInfo(GET, "/api/v1/review/**", null),

            // static resources
            new RequestInfo(GET, "/docs/**", null),
            new RequestInfo(GET, "/*.ico", null),
            new RequestInfo(GET, "/resources/**", null),
            new RequestInfo(GET, "/index.html", null),
            new RequestInfo(GET, "/error", null),

            // Swagger UI 및 API 문서 관련 요청
            new RequestInfo(GET, "/swagger-ui/**", null),
            new RequestInfo(GET, "/v3/api-docs/**", null),
            new RequestInfo(GET, "/swagger-resources/**", null),
            new RequestInfo(GET, "/webjars/**", null),

            // 정적 아이콘 요청
            new RequestInfo(GET, "/favicon.ico", null),
            new RequestInfo(GET, "/apple-touch-icon.png", null)

    );

    private final ConcurrentHashMap<String, RequestMatcher> reqMatcherCacheMap = new ConcurrentHashMap<>();

    /**
     * 최소 권한이 주어진 요청에 대한 RequestMatcher 반환
     * @param minRole 최소 권한 (Nullable)
     * @return 생성된 RequestMatcher
     */
    public RequestMatcher getRequestMatchersByMinRole(@Nullable UserRole minRole) {
        var key = getKeyByRole(minRole);
        return reqMatcherCacheMap.computeIfAbsent(key, k ->
                new OrRequestMatcher(REQUEST_INFO_LIST.stream()
                        .filter(reqInfo -> isAccessible(reqInfo.minRole(), minRole))
                        .map(reqInfo -> new AntPathRequestMatcher(reqInfo.pattern(),
                                reqInfo.method().name()))
                        .toArray(AntPathRequestMatcher[]::new)));
    }

    private boolean isAccessible(@Nullable UserRole requiredRole, @Nullable UserRole currentUserRole) {
        if (requiredRole == null) return true; // 누구나 접근 가능
        if (currentUserRole == null) return false; // 권한 없음
        return currentUserRole.ordinal() >= requiredRole.ordinal(); // ADMIN이면 MEMBER 포함
    }

    private String getKeyByRole(@Nullable UserRole minRole) {
        return minRole == null ? "VISITOR" : minRole.name();
    }

    private record RequestInfo(HttpMethod method, String pattern, UserRole minRole) {}

}
