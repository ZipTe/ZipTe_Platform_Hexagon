package com.zipte.platform.server.domain.region;

public class RegionFixtures {

    public static Region stub(String code, String address) {
        return Region.builder()
                .code(code)
                .address(address)
                .build();
    }

    public static Region 성남시() {
        return stub("4113000000", "경기도 성남시");
    }

    public static Region 분당구() {
        return stub("4113500000", "경기도 성남시 분당구");
    }

    public static Region 야탑동() {
        return stub("4113510700", "경기도 성남시 분당구 야탑동");
    }

    public static Region 종로구() {
        return stub("1111000000", "서울특별시 종로구");
    }

    public static Region 종로구_청운동() {
        return stub("1111010100", "서울특별시 종로구 청운동");
    }



}
