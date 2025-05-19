package com.zipte.platform.server.adapter.in.web;

import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.security.oauth2.domain.PrincipalDetails;
import com.zipte.platform.server.adapter.in.web.dto.request.UserUpdateRequest;
import com.zipte.platform.server.adapter.in.web.dto.response.UserMyInfoResponse;
import com.zipte.platform.server.adapter.in.web.swagger.UserApiSpec;
import com.zipte.platform.server.application.in.user.GetUserUseCase;
import com.zipte.platform.server.application.in.user.UpdateUserUseCase;
import com.zipte.platform.server.domain.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserApi implements UserApiSpec {

    private final GetUserUseCase getService;
    private final UpdateUserUseCase updateService;

    @GetMapping("/mypage")
    public ApiResponse<UserMyInfoResponse> getMyInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) {

        log.info("[getMyInfo] {}", principalDetails);
        User user = getService.getMyInfo(principalDetails.getId());

        return ApiResponse.ok(UserMyInfoResponse.from(user));
    }

    @PutMapping()
    public ApiResponse<String> updateMyInfo(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                            @ModelAttribute @Valid UserUpdateRequest request) {

        updateService.updateUser(principalDetails.getId(), request);
        return ApiResponse.ok("수정되었습니다.");
    }

}
