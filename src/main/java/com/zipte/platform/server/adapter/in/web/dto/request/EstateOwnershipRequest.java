package com.zipte.platform.server.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EstateOwnershipRequest {

    @NotNull(message = "유저 ID는 필수입니다.")
    private Long userId;

    @NotNull(message = "아파트 코드(kaptCode)는 필수입니다.")
    @Size(min = 1, message = "아파트 코드는 비어 있을 수 없습니다.")
    private String kaptCode;

    /// 사용자의 현위치
    @NotNull(message = "경도는 필수입니다.")
    private Double longitude;

    @NotNull(message = "위도는 필수입니다.")
    private Double latitude;

    @NotNull(message = "구매 날짜는 필수입니다.")
    private LocalDateTime boughtAt;

}
