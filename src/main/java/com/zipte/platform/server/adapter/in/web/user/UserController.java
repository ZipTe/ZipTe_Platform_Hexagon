package com.zipte.platform.server.adapter.in.web.user;

import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.security.oauth2.domain.PrincipalDetails;
import com.zipte.platform.server.adapter.in.web.dto.request.UserUpdateRequest;
import com.zipte.platform.server.adapter.in.web.dto.response.UserResponse;
import com.zipte.platform.server.adapter.in.web.swagger.UserApiSpec;
import com.zipte.platform.server.application.in.user.GetUserUseCase;
import com.zipte.platform.server.application.in.user.UpdateUserUseCase;
import com.zipte.platform.server.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements UserApiSpec {

    private final GetUserUseCase getService;
    private final UpdateUserUseCase updateService;

    @GetMapping("/mypage")
    public ApiResponse<UserResponse> getMyInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        User user = getService.getMyInfo(principalDetails.getId());

        return ApiResponse.ok(UserResponse.from(user));
    }

    @PutMapping()
    public ApiResponse<String> updateMyInfo(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody UserUpdateRequest request) {

        updateService.updateUser(principalDetails.getId(), request);
        return ApiResponse.ok("수정되었습니다.");
    }

}
