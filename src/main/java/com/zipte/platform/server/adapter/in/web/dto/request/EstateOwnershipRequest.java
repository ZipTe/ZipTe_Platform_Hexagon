package com.zipte.platform.server.adapter.in.web.dto.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EstateOwnershipRequest {

    private Long userId;

    private String kaptCode;

    /// 사용자의 현위치
    private Double longitude;
    private Double latitude;

    // 아파트의 소유 날짜
    private LocalDateTime boughtAt;

}
