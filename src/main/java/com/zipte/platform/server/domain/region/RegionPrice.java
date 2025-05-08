package com.zipte.platform.server.domain.region;

import com.zipte.platform.server.domain.BaseDomain;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class RegionPrice extends BaseDomain {

    private Long id;

    private String regionCode;

    private Double under15;

    private Double between15and20;

    private Double between20and30;

    private Double upper30;

    /// 정적 팩토리 메서드
    private static RegionPrice from(String regionCode, Map<String, Double> averagePrices) {
        return RegionPrice.builder()
                .regionCode(regionCode)
                .build();
    }

}
