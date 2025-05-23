package com.zipte.platform.server.adapter.in.web.swagger;

import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.core.response.pageable.PageRequest;
import com.zipte.platform.core.response.pageable.PageResponse;
import com.zipte.platform.server.adapter.in.web.dto.response.EstateDetailResponse;
import com.zipte.platform.server.adapter.in.web.dto.response.EstateListResponse;
import com.zipte.platform.server.adapter.in.web.dto.response.EstatePriceListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "아파트 API", description = "아파트 관련 조회 API")
public interface EstateApiSpec {

    @Operation(
            summary = "아파트 상세 정보 조회",
            description = "아파트 코드 또는 이름을 통해 상세 정보를 조회합니다."
    )
    ApiResponse<EstateDetailResponse> getEstate(
            @Parameter(description = "아파트 코드", example = "A46393018")
            @RequestParam(value = "code", required = false) String code,

            @Parameter(description = "아파트 이름", example = "야탑장미마을동부")
            @RequestParam(value = "name", required = false) String name
    );

    @Operation(
            summary = "특정 지역의 아파트 목록 조회",
            description = "지역명을 기반으로 해당 지역의 아파트 목록을 페이징 처리하여 반환합니다."
    )
    ApiResponse<PageResponse<EstateListResponse>> getEstateByRegion(
            @Parameter(description = "지역명", required = true, example = "강남구")
            @RequestParam(value = "region") String region,

            @ParameterObject PageRequest pageRequest
    );

    @Operation(
            summary = "특정 좌표 근처 아파트 목록 조회",
            description = "지정한 경도, 위도, 반경 값으로부터 근처에 위치한 아파트 목록을 반환합니다."
    )
    ApiResponse<List<EstateListResponse>> getEstateByLocation(
            @Parameter(description = "경도", required = true, example = "127.118904")
            @RequestParam(value = "longitude") double longitude,

            @Parameter(description = "위도", required = true, example = "37.382698")
            @RequestParam(value = "latitude") double latitude,

            @Parameter(description = "반경 (km 단위)", required = true, example = "1")
            @RequestParam(value = "radius") double radius
    );

    @Operation(
            summary = "특정 좌표 근처 아파트 및 매물 목록 조회",
            description = "지정한 좌표와 반경을 기준으로 근처의 아파트 및 해당 아파트에 등록된 매물 정보를 함께 조회합니다."
    )
    ApiResponse<List<EstateListResponse>> getEstateByLocationByKaptCode(
            @Parameter(description = "경도", required = true, example = "127.118904")
            @RequestParam(value = "longitude") double longitude,

            @Parameter(description = "위도", required = true, example = "37.382698")
            @RequestParam(value = "latitude") double latitude,

            @Parameter(description = "반경 (km 단위)", required = true, example = "1.0")
            @RequestParam(value = "radius") double radius
    );

    @Operation(
            summary = "AI를 통한 아파트 정보 요약 조회",
            description = "Gemini를 바탕으로 아파트의 특징를 요약하여 조회합니다."
    )
    ApiResponse<String> getEstateDetail(
            @Parameter(description = "아파트 코드", required = true, example = "A46378823")
            @PathVariable String kaptCode);



    @Operation(
            summary = "아파트 가격 정보 조회",
            description = "아파트 코드를 통해 가격 정보를 조회합니다."
    )
    ApiResponse<List<EstatePriceListResponse>> getPrice(
            @Parameter(description = "아파트 코드", required = true, example = "A46392821")
            @PathVariable String kaptCode);



    @Operation(
            summary = "아파트 가격 정보 조회",
            description = "아파트 코드와 평수를 통해 가격 정보를 조회합니다."
    )
    ApiResponse<List<EstatePriceListResponse>> getPriceByCodeAndArea(
            @Parameter(description = "아파트 코드", required = true, example = "A46392821")
            @RequestParam String kaptCode,

            @Parameter(description = "면적", required = true, example = "83.73")
            @RequestParam double area);
}
