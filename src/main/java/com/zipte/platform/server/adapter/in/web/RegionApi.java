package com.zipte.platform.server.adapter.in.web;

import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.server.adapter.in.web.dto.response.RegionPriceResponse;
import com.zipte.platform.server.adapter.in.web.dto.response.RegionResponse;
import com.zipte.platform.server.adapter.in.web.swagger.RegionApiSpec;
import com.zipte.platform.server.application.in.region.RegionPriceUseCase;
import com.zipte.platform.server.application.in.region.RegionUseCase;
import com.zipte.platform.server.domain.region.Region;
import com.zipte.platform.server.domain.region.RegionPrice;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/region")
@RequiredArgsConstructor
public class RegionApi implements RegionApiSpec {

    private final RegionUseCase regionService;

    private final RegionPriceUseCase priceService;


    @GetMapping("/{code}")
    public ApiResponse<RegionResponse> getRegion(@PathVariable String code) {
        Region region = regionService.loadRegion(code);

        return ApiResponse.ok(RegionResponse.from(region));
    }

    @GetMapping("/list/{code}")
    public ApiResponse<List<RegionResponse>> getRegionList(@PathVariable String code) {
        List<Region> regions = regionService.loadChildRegionsByCode(code);
        return ApiResponse.ok(RegionResponse.from(regions));

    }

    @GetMapping("/price/{code}")
    public ApiResponse<RegionPriceResponse> getRegionDetail(@PathVariable String code) {
        RegionPrice regionPrice = priceService.loadRegionPriceByCode(code);

        return ApiResponse.ok(RegionPriceResponse.from(regionPrice));
    }
}
