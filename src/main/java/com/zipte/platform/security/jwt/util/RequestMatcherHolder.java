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

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Component
public class RequestMatcherHolder {

    private static final List<RequestInfo> REQUEST_INFO_LIST = List.of(
            // 개발 모드
            new RequestInfo(GET, "/**", null),
            new RequestInfo(POST, "/oauth2", null),

            // auth
            new RequestInfo(POST, "/login/**", null),
            new RequestInfo(GET, "/", null),
            new RequestInfo(GET, "/oauth2/session-user", null),
            new RequestInfo(POST, "/oauth2", null),
            new RequestInfo(POST, "/reissue", UserRole.MEMBER),

            // user
            new RequestInfo(POST, "/signup", null),
            new RequestInfo(GET, "/users/**", UserRole.MEMBER),

            // admin
            new RequestInfo(GET, "/api/member/**", UserRole.MEMBER),
            new RequestInfo(POST, "/api/admin/**", UserRole.ADMIN),

            // static resources
            new RequestInfo(GET, "/docs/**", null),
            new RequestInfo(GET, "/*.ico", null),
            new RequestInfo(GET, "/resources/**", null),
            new RequestInfo(GET, "/error", null)
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
                        .filter(reqInfo -> Objects.equals(reqInfo.minRole(), minRole))
                        .map(reqInfo -> new AntPathRequestMatcher(reqInfo.pattern(),
                                reqInfo.method().name()))
                        .toArray(AntPathRequestMatcher[]::new)));
    }

    private String getKeyByRole(@Nullable UserRole minRole) {
        return minRole == null ? "VISITOR" : minRole.name();
    }

    private record RequestInfo(HttpMethod method, String pattern, UserRole minRole) {

    }

}
