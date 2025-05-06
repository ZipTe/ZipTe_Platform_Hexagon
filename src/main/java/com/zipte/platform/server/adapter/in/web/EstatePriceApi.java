package com.zipte.platform.server.adapter.in.web;

import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.server.adapter.in.web.dto.response.EstatePriceListResponse;
import com.zipte.platform.server.application.in.estate.EstatePriceUseCase;
import com.zipte.platform.server.domain.estate.EstatePrice;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/estate/price")
@RequiredArgsConstructor
public class EstatePriceApi {

    private final EstatePriceUseCase priceService;


    @GetMapping("/{kaptCode}")
    public ApiResponse<List<EstatePriceListResponse>> getPrice(@PathVariable String kaptCode) {

        List<EstatePrice> list = priceService.getEstatePriceByCode(kaptCode);

        return ApiResponse.ok(EstatePriceListResponse.from(list));
    }

    @GetMapping()
    public ApiResponse<List<EstatePriceListResponse>> getPriceByCodeAndArea(
            @RequestParam String kaptCode,
            @RequestParam double area) {

        List<EstatePrice> list = priceService.getEstatePriceByCode(kaptCode, area);

        return ApiResponse.ok(EstatePriceListResponse.from(list));
    }

}
