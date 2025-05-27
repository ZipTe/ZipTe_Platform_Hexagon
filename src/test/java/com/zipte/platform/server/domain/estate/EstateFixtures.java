package com.zipte.platform.server.domain.estate;

import java.util.Arrays;

public class EstateFixtures {

    public static Estate stub() {

        Location location = Location.builder()
                .type("Point")
                .coordinates(Arrays.asList(
                        127.1280,  // 경도
                        37.4116)   // 위도
                )
                .build();

        return Estate.builder()
                .kaptCode("kaptCode")
                .location(location)
                .build();
    }

    public static Estate stub(String kaptCode) {

        Location location = Location.builder()
                .type("Point")
                .coordinates(Arrays.asList(
                        127.1280,  // 경도
                        37.4116)   // 위도
                )
                .build();

        return Estate.builder()
                .kaptCode(kaptCode)
                .location(location)
                .build();
    }



}
