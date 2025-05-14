package com.zipte.platform.server.adapter.in.web.swagger;

import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.core.response.pageable.PageRequest;
import com.zipte.platform.core.response.pageable.PageResponse;
import com.zipte.platform.security.oauth2.domain.PrincipalDetails;
import com.zipte.platform.server.domain.notification.Notification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Tag(name = "알림 API", description = "알림 관련 조회 API")
public interface NotificationApiSpec {


    @Operation(
            summary = "나의 알림 센터 알림 여부 조회",
            description = "JWT 를 통해서 나의 알림 센터의 알림 여부를 조회합니다.",
            security = @SecurityRequirement(name = "JWT")
    )
    ApiResponse<String> getNotification(
            @AuthenticationPrincipal PrincipalDetails principalDetails);


    @Operation(
            summary = "나의 알림 센터 알림 목록 조회",
            description = "JWT 를 통해서 나의 알림 목록을 조회합니다.",
            security = @SecurityRequirement(name = "JWT")
    )
    ApiResponse<PageResponse<Notification>> list(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @ParameterObject PageRequest pageRequest);

}
