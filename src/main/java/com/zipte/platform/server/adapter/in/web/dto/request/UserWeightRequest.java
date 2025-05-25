package com.zipte.platform.server.adapter.in.web.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserWeightRequest {

    @NotNull(message = "편의시설 가중치는 필수입니다.")
    @Min(value = 1, message = "편의시설 가중치는 1 이상이어야 합니다.")
    @Max(value = 100, message = "편의시설 가중치는 100 이하이어야 합니다.")
    private Integer convenience;        // 편의시설 (마트, 병원 등 근접성)

    @NotNull(message = "교통 가중치는 필수입니다.")
    @Min(value = 1, message = "교통 가중치는 1 이상이어야 합니다.")
    @Max(value = 100, message = "교통 가중치는 100 이하이어야 합니다.")
    private Integer transportation;     // 교통 (지하철, 버스 정류장까지 소요 시간)

    @NotNull(message = "지역 선호도 가중치는 필수입니다.")
    @Min(value = 1, message = "지역 선호도 가중치는 1 이상이어야 합니다.")
    @Max(value = 100, message = "지역 선호도 가중치는 100 이하이어야 합니다.")
    private Integer regionPreference;   // 지역 선호도

    @NotNull(message = "공원 접근성 가중치는 필수입니다.")
    @Min(value = 1, message = "공원 접근성 가중치는 1 이상이어야 합니다.")
    @Max(value = 100, message = "공원 접근성 가중치는 100 이하이어야 합니다.")
    private Integer parkAccess;         // 공원 접근성

    @NotNull(message = "직장/학교와의 거리 가중치는 필수입니다.")
    @Min(value = 1, message = "직장/학교와의 거리 가중치는 1 이상이어야 합니다.")
    @Max(value = 100, message = "직장/학교와의 거리 가중치는 100 이하이어야 합니다.")
    private Integer distanceToWork;     // 직장/학교와의 거리

}
