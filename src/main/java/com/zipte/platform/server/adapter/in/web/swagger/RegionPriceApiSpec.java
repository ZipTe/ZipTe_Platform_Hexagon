package com.zipte.platform.server.adapter.in.web.swagger;

import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.server.adapter.in.web.dto.response.RegionPriceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "지역 가격 API", description = "지역 가격 관련 조회 API")
public interface RegionPriceApiSpec {

    @Operation(
            summary = "지역 가격 정보 조회",
            description = "지역 코드를 통해 가격 정보를 조회합니다."
    )
    ApiResponse<RegionPriceResponse> getRegionDetail(
            @Parameter(description = "지역 코드", required = true, example = "4113510700")
            @PathVariable String code);


    @Operation(
            summary = "지역 가격 업데이트 함수",
            description = "추후, 배치 처리를 위해 업데이트 할 예정입니다. / 자치구 단위부터 가능합니다."
    )
    ApiResponse<String> saveOne(
            @Parameter(description = "지역 코드", required = true, example = "4113510700")
            @PathVariable String code);


}
