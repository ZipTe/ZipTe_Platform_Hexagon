package com.zipte.platform.server.domain.review;

import com.zipte.platform.server.domain.estate.Estate;
import com.zipte.platform.server.domain.estate.Location;

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

}
