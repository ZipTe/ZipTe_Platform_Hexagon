package com.zipte.platform.server.adapter.in.web.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EstateLocationRequest {
    private double longitude; // 경도
    private double latitude;  // 위도
    private double radius;    // 검색 반경 (km 단위)
}
