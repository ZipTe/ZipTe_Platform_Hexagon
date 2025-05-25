package com.zipte.platform.server.domain.estate;

import java.util.Arrays;

public class EstateFixtures {

    public static Estate stub(){

        Location location = Location.builder()
                .coordinates(Arrays.asList(
                        127.123456,
                        37.12345
                )).build();

        return Estate.builder()
                .id("test-estate-id")
                .kaptCode("TEST-KAPT-CODE-1234")
                .pricePerSquareMeter("test-price-1500000")
                .kaptAddr("test-경기도 성남시 분당구 테스트로 123")
                .kaptMparea_135("test-area-135")
                .kaptMparea_136("test-area-136")
                .kaptMparea_60("test-area-60")
                .kaptMparea_85("test-area-85")
                .kaptName("테스트힐스테이트아파트")
                .location(location) // test 위도/경도
                .convenientFacility("test-편의시설1, test-편의시설2")
                .educationFacility("test-초등학교, test-중학교")
                .kaptdPcnt("test-85.0")
                .kaptdPcntu("test-82.3")
                .kaptdWtimebus("test-버스 15분")
                .kaptdWtimesub("test-지하철 10분")
                .subwayLine("test-2호선")
                .subwayStation("test-강남역")
                .welfareFacility("test-주민센터, test-복지관")
                .build();


    }

}
