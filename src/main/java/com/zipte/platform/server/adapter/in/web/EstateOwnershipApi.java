package com.zipte.platform.server.adapter.in.web;

import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.server.adapter.in.web.dto.request.EstateOwnershipRequest;
import com.zipte.platform.server.application.in.estateOwnership.EstateOwnershipUseCase;
import com.zipte.platform.server.domain.estateOwnership.EstateOwnership;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ownership")
@RequiredArgsConstructor
public class EstateOwnershipApi {

    private final EstateOwnershipUseCase service;

    @PostMapping
    public ApiResponse<String> create(@RequestBody EstateOwnershipRequest request) {

        EstateOwnership ownership = service.createOwnership(request);

        return ApiResponse.created("정상적으로 등록되었습니다.");
    }

    @DeleteMapping
    public ApiResponse<String> delete(@RequestBody EstateOwnershipRequest request) {

        service.deleteOwnership(request.getUserId(), request.getKaptCode());

        return ApiResponse.ok("정상적으로 삭제 되었습니다.");
    }
}
