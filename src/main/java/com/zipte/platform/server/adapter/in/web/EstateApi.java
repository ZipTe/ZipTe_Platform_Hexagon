package com.zipte.platform.server.adapter.in.web;

import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.core.response.pageable.PageRequest;
import com.zipte.platform.core.response.pageable.PageResponse;
import com.zipte.platform.server.adapter.in.web.dto.response.EstateDetailResponse;
import com.zipte.platform.server.adapter.in.web.dto.response.EstateListResponse;
import com.zipte.platform.server.adapter.in.web.dto.response.EstatePriceListResponse;
import com.zipte.platform.server.adapter.in.web.swagger.EstateApiSpec;
import com.zipte.platform.server.application.in.estate.EstatePriceUseCase;
import com.zipte.platform.server.application.in.estate.GetEstateUseCase;
import com.zipte.platform.server.application.in.external.OpenAiUseCase;
import com.zipte.platform.server.domain.estate.Estate;
import com.zipte.platform.server.domain.estate.EstatePrice;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/estate")
@RequiredArgsConstructor
public class EstateApi implements EstateApiSpec {

    private final GetEstateUseCase getService;

    /// 가격 의존성
    private final EstatePriceUseCase priceService;

    /// AI 의존성
    private final OpenAiUseCase openAiService;

    // 상세 정보 조회
    @GetMapping
    public ApiResponse<EstateDetailResponse> getEstate(
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "name", required = false) String name) {

        Estate estate;

        if (code != null) {
            estate = getService.loadEstateByCode(code)
                    .orElseThrow(() -> new EntityNotFoundException("해당하는 아파트가 존재하지 않습니다."));
        } else if (name != null) {
            estate = getService.loadEstateByName(name)
                    .orElseThrow(() -> new EntityNotFoundException("해당하는 아파트가 존재하지 않습니다."));
        } else {
            throw new IllegalArgumentException("최소 하나 이상의 요청 파라미터가 필요합니다.");
        }

        return ApiResponse.created(EstateDetailResponse.from(estate));
    }

    // 특정 지역 아파트 목록 조회
    @GetMapping("/list")
    public ApiResponse<PageResponse<EstateListResponse>> getEstateByRegion(
            @RequestParam(value = "region") String region,
            PageRequest pageRequest) {

        Pageable pageable = org.springframework.data.domain.PageRequest.of(
                pageRequest.getPage() - 1,
                pageRequest.getSize(),
                Sort.by(Sort.Direction.DESC, "id")
        );

        Page<Estate> result = getService.loadEstatesByRegion(region, pageable);

        List<EstateListResponse> dtolist = EstateListResponse.from(result.getContent());

        return ApiResponse.ok(new PageResponse<>(dtolist, pageRequest, result.getTotalElements()));
    }

    // 특정 좌표 근처의 아파트 목록 조회
    @GetMapping("/list/location")
    public ApiResponse<List<EstateListResponse>> getEstateByLocation(
            @RequestParam(value = "longitude") double longitude,
            @RequestParam(value = "latitude") double latitude,
            @RequestParam(value = "radius") double radius) {

        return ApiResponse.ok(getService.loadEstatesNearBy(longitude, latitude, radius));

    }

    // 특정 좌표 근처의 아파트 및 매물 목록 조회
    @GetMapping("/list/location/property")
    public ApiResponse<List<EstateListResponse>> getEstateByLocationByKaptCode(
            @RequestParam(value = "longitude") double longitude,
            @RequestParam(value = "latitude") double latitude,
            @RequestParam(value = "radius") double radius) {

        List<EstateListResponse> list = getService.loadEstatesNearByProperty(longitude, latitude, radius);

        return ApiResponse.ok(list);

    }

    @GetMapping("/compare")
    public ApiResponse<List<EstateDetailResponse>> getEstateByCompare(
            @RequestParam(value = "first") String first,
            @RequestParam(value = "second") String second
    ) {
        List<Estate> estates = getService.loadEstatesByCompare(List.of(first, second));

        return ApiResponse.ok(EstateDetailResponse.from(estates));
    }


    /// AI 기반 특징 요약
    @GetMapping("/ai/{kaptCode}")
    public ApiResponse<String> getEstateDetail(@PathVariable String kaptCode) {
        String result = openAiService.getKaptCharacteristic(kaptCode);

        return ApiResponse.ok(result);
    }


    /// 가격 조회
    @GetMapping("/price")
    public ApiResponse<List<EstatePriceListResponse>> getPriceByCodeAndArea(
            @RequestParam String kaptCode,
            @RequestParam double area) {

        List<EstatePrice> list = priceService.getEstatePriceByCode(kaptCode, area);

        return ApiResponse.ok(EstatePriceListResponse.from(list));
    }


    @GetMapping("/price/{kaptCode}")
    public ApiResponse<List<EstatePriceListResponse>> getPrice(@PathVariable String kaptCode) {

        List<EstatePrice> list = priceService.getEstatePriceByCode(kaptCode);

        return ApiResponse.ok(EstatePriceListResponse.from(list));
    }
}
