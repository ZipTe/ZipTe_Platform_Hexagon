package com.zipte.platform.server.adapter.in.web.swagger;

import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.server.adapter.in.web.dto.response.RegionPriceResponse;
import com.zipte.platform.server.adapter.in.web.dto.response.RegionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "지역 API", description = "지역 관련 조회 API")
public interface RegionApiSpec {


    @Operation(
            summary = "지역 상세 정보 조회",
            description = "지역 코드를 통해 상세 정보를 조회합니다."
    )
    ApiResponse<RegionResponse> getRegion(

            @Parameter(description = "지역 코드", example = "4113000000")
            @PathVariable String code);


    @Operation(
            summary = "하위 지역 목록 정보 조회",
            description = "지역 코드를 통해 하위 지역 목록 정보를 조회합니다."
    )
    ApiResponse<List<RegionResponse>> getRegionList(

            @Parameter(description = "지역 코드", example = "4113000000")
            @PathVariable String code);


    @Operation(
            summary = "지역 가격 정보 조회",
            description = "지역 코드를 통해 가격 정보를 조회합니다."
    )
    ApiResponse<RegionPriceResponse> getRegionDetail(
            @Parameter(description = "지역 코드", required = true, example = "4113510700")
            @PathVariable String code);
}
