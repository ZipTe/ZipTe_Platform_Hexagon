package com.zipte.platform.server.adapter.in.web.dto.response;

import com.zipte.platform.server.domain.estate.Estate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EstateLocationResponse {

    // 좌표
    private double latitude;     // 위도
    private double longitude;    // 경도

    public static EstateLocationResponse from(Estate estate) {
        return EstateLocationResponse.builder()
                .latitude(estate.getLocation().getLatitude())
                .longitude(estate.getLocation().getLongitude())
                .build();
    }

}
