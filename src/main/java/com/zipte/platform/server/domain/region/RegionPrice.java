package com.zipte.platform.server.domain.region;

import com.zipte.platform.server.domain.BaseDomain;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class RegionPrice extends BaseDomain {

    private String regionCode;

    private Double under15;

    private Double between15and20;

    private Double between20and30;

    private Double upper30;

    /// 정적 팩토리 메서드
    public static RegionPrice of(String regionCode, Map<String, Double> averagePrices) {
        return RegionPrice.builder()
                .regionCode(regionCode)
                .under15(averagePrices.get("15평 이하"))
                .between15and20(averagePrices.get("15~20평"))
                .between20and30(averagePrices.get("20~25평"))
                .upper30(averagePrices.get("30평 이상"))
                .build();
    }

}
