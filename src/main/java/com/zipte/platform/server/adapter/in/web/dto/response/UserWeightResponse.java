package com.zipte.platform.server.adapter.in.web.dto.response;

import com.zipte.platform.server.domain.user.UserWeight;
import lombok.Builder;

@Builder
public record UserWeightResponse(
        Long id,
        Long userId,
        int convenienceWeight,      // 편의시설 (마트, 병원 등 근접성)
        int transportationWeight,     // 교통 (지하철, 버스 정류장까지 소요 시간)
        int regionPreferenceWeight,   // 지역 선호도
        int parkAccessWeight,         // 공원 접근성
        int distanceToWorkWeight     // 직장/학교와의 거리
) {

    public static UserWeightResponse from(UserWeight userWeight) {
        return UserWeightResponse.builder()
                .id(userWeight.getId())
                .userId(userWeight.getUserId())
                .convenienceWeight(userWeight.getConvenienceWeight())
                .transportationWeight(userWeight.getTransportationWeight())
                .regionPreferenceWeight(userWeight.getRegionPreferenceWeight())
                .parkAccessWeight(userWeight.getParkAccessWeight())
                .distanceToWorkWeight(userWeight.getDistanceToWorkWeight())
                .build();
    }
}
