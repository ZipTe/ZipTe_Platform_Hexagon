package com.zipte.platform.server.adapter.in.web.dto.request;

import lombok.Data;

@Data
public class UserWeightRequest {

    private Long id;

    private Long userId;

    private int convenience;        // 편의시설 (마트, 병원 등 근접성)

    private int transportation;     // 교통 (지하철, 버스 정류장까지 소요 시간)

    private int regionPreference;   // 지역 선호도

    private int parkAccess;         // 공원 접근성

    private int distanceToWork;     // 직장/학교와의 거리


}
