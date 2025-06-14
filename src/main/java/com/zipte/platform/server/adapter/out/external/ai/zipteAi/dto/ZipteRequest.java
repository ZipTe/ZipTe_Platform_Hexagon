package com.zipte.platform.server.adapter.out.external.ai.zipteAi.dto;

import com.zipte.platform.server.domain.user.UserWeight;
import lombok.Builder;
import lombok.Data;

@Data
public class ZipteRequest {

    Long userId;

    int convenienceWeight;        // 편의시설 (마트, 병원 등 근접성)

    int transportationWeight;     // 교통 (지하철, 버스 정류장까지 소요 시간)

    int regionPreferenceWeight;   // 지역 선호도

    int parkAccessWeight;         // 공원 접근성

    int distanceToWorkWeight;     // 직장/학교와의 거리

    @Builder
    private ZipteRequest(Long userId, int convenienceWeight, int transportationWeight, int regionPreferenceWeight, int parkAccessWeight, int distanceToWorkWeight) {
        this.userId = userId;
        this.convenienceWeight = convenienceWeight;
        this.transportationWeight = transportationWeight;
        this.regionPreferenceWeight = regionPreferenceWeight;
        this.parkAccessWeight = parkAccessWeight;
        this.distanceToWorkWeight = distanceToWorkWeight;
    }

    /// 생성자
    public static ZipteRequest from(UserWeight userWeight) {
        return ZipteRequest.builder()
                .userId(userWeight.getUserId())
                .convenienceWeight(userWeight.getConvenienceWeight())
                .transportationWeight(userWeight.getTransportationWeight())
                .regionPreferenceWeight(userWeight.getRegionPreferenceWeight())
                .parkAccessWeight(userWeight.getParkAccessWeight())
                .distanceToWorkWeight(userWeight.getDistanceToWorkWeight())
                .build();

    }
}
