package com.zipte.platform.server.domain.region;

import java.util.HashMap;
import java.util.Map;

public class RegionPriceFixtures {

    public static RegionPrice stub(String region) {
        Map<String, Double> averagePrices = new HashMap<>();
        averagePrices.put("15평 이하", 80000.0);
        averagePrices.put("15~20평", 100000.0);
        averagePrices.put("20~25평", 120000.0);
        averagePrices.put("25~30평", 140000.0);
        averagePrices.put("30평 이상", 160000.0);

        return RegionPrice.of(region, averagePrices); // 예: 분당구 코드
    }

}

