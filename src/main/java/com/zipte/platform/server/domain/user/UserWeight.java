package com.zipte.platform.server.domain.user;

import com.zipte.platform.server.domain.BaseDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserWeight extends BaseDomain {

    private Long id;                      // 아이디
    private int convenienceWeight;        // 편의시설 (마트, 병원 등 근접성)
    private int transportationWeight;     // 교통 (지하철, 버스 정류장까지 소요 시간)
    private int regionPreferenceWeight;   // 지역 선호도
    private int parkAccessWeight;         // 공원 접근성
    private int distanceToWorkWeight;     // 직장/학교와의 거리

    private UserWeight of(int convenience, int trans, int region, int parkAccess, int distanceToWork) {
        return UserWeight.builder()
                .convenienceWeight(convenience)
                .transportationWeight(trans)
                .regionPreferenceWeight(region)
                .parkAccessWeight(parkAccess)
                .distanceToWorkWeight(distanceToWork)
                .build();
    }

}
