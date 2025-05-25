package com.zipte.platform.server.adapter.in.web;

import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.security.oauth2.domain.PrincipalDetails;
import com.zipte.platform.server.adapter.in.web.dto.request.UserWeightRequest;
import com.zipte.platform.server.adapter.in.web.dto.response.UserWeightResponse;
import com.zipte.platform.server.application.in.user.UserWeightUseCase;
import com.zipte.platform.server.domain.user.UserWeight;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/weight")
public class UserWeightApi {

    private final UserWeightUseCase userWeightService;

    @PostMapping
    public ApiResponse<String> create(@RequestBody UserWeightRequest request) {
        userWeightService.createWeight(request);

        return ApiResponse.created("나만의 가중치값이 생성되었습니다.");
    }

    @GetMapping("/my-value")
    public ApiResponse<UserWeightResponse> getMyWeight(@AuthenticationPrincipal PrincipalDetails principalDetails) {

        UserWeight weight = userWeightService.getWeight(principalDetails.getId());

        return ApiResponse.ok(UserWeightResponse.from(weight));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long id) {
        userWeightService.removeWeight(principalDetails.getId(), id);

        return ApiResponse.ok("정상적으로 삭제 되었습니다.");
    }
}
