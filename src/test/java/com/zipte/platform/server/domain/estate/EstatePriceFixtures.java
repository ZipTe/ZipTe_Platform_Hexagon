package com.zipte.platform.server.domain.estate;

public class EstatePriceFixtures {


    public static EstatePrice stub(String kaptCode) {

        return EstatePrice.builder()
                .id("id")
                .kaptCode(kaptCode)
                .kaptName("kaptName")
                .exclusiveArea(0.0)
                .floor(1)
                .price("1000000")
                .build();
    }

}
