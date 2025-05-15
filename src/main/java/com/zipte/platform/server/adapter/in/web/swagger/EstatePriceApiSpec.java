package com.zipte.platform.server.adapter.in.web.swagger;

import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.server.adapter.in.web.dto.response.EstatePriceListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "아파트 실거래가 API", description = "아파트 가격 관련 조회 API")
public interface EstatePriceApiSpec {


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

