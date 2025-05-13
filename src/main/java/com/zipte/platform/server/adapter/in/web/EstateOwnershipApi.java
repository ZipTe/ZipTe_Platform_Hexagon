package com.zipte.platform.server.adapter.in.web;

import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.security.oauth2.domain.PrincipalDetails;
import com.zipte.platform.server.adapter.in.web.dto.request.EstateOwnershipRequest;
import com.zipte.platform.server.adapter.in.web.swagger.EstateOwnerShipApiSpec;
import com.zipte.platform.server.application.in.estateOwnership.EstateOwnershipUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ownership")
@RequiredArgsConstructor
public class EstateOwnershipApi implements EstateOwnerShipApiSpec {

    private final EstateOwnershipUseCase service;

    @PostMapping
    public ApiResponse<String> create(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody EstateOwnershipRequest request) {


        /// 유저 id 넣기
        Long userId = principalDetails.getId();
        request.setUserId(userId);

        /// 서비스
        service.createOwnership(request);

        return ApiResponse.created("정상적으로 등록되었습니다.");
    }

    @DeleteMapping
    public ApiResponse<String> delete(@RequestBody EstateOwnershipRequest request) {

        service.deleteOwnership(request.getUserId(), request.getKaptCode());

        return ApiResponse.ok("정상적으로 삭제 되었습니다.");
    }
}
