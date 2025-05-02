package com.zipte.platform.server.adapter.in.web.dto.response;

import com.zipte.platform.server.domain.region.Region;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record RegionResponse(
        @Schema(description = "법정동 코드", example = "1111010100") String code,
        @Schema(description = "법정동 이름", example = "서울특별시 종로구 청운동") String name){

    /// 생성자
    public static RegionResponse from(Region region) {
        return RegionResponse.builder()
                .code(region.getCode())
                .name(region.getAddress())
                .build();
    }

    public static List<RegionResponse> from(List<Region> regions) {
        return regions.stream()
                .map(RegionResponse::from)
                .toList();

    }
}
