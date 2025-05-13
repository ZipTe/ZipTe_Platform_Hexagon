package com.zipte.platform.server.adapter.in.web;

import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.server.adapter.in.web.dto.response.RegionPriceResponse;
import com.zipte.platform.server.adapter.in.web.swagger.RegionPriceApiSpec;
import com.zipte.platform.server.application.in.region.RegionPriceUseCase;
import com.zipte.platform.server.domain.region.RegionPrice;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/region/price")
@RequiredArgsConstructor
public class RegionPriceApi implements RegionPriceApiSpec {

    private final RegionPriceUseCase service;


    @PostMapping("/{code}")
    public ApiResponse<String> saveOne(@PathVariable String code) {
        service.saveRegionPrice(code);
        return ApiResponse.created("정상적으로 업데이트 되었습니다.");
    }

    @GetMapping("/{code}")
    public ApiResponse<RegionPriceResponse> getRegionDetail(@PathVariable String code) {
        RegionPrice regionPrice = service.loadRegionPriceByCode(code);

        return ApiResponse.ok(RegionPriceResponse.from(regionPrice));
    }

}
