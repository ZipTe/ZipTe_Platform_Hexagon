package com.zipte.platform.server.adapter.in.web.dto.response;

import com.zipte.platform.server.domain.estate.Estate;
import lombok.Builder;
import lombok.Data;

import java.util.*;

@Data
@Builder
public class EstateDetailResponse {

    // 아파트 기본 정보
    private String complexCode;  // 아파트 단지 코드
    private String complexName;  // 아파트 단지명
    private String address;      // 주소

    // 좌표
    private EstateLocationResponse location;

    // 가격 정보 (제곱미터당 가격)
    private String pricePerSqm;

    // 면적 및 편의시설 정보
    private EstateAreaResponse area;
    private EstateFacilityResponse facility;

    public static EstateDetailResponse from(Estate estate) {
        return EstateDetailResponse.builder()
                .complexCode(estate.getKaptCode())
                .complexName(estate.getKaptName())
                .address(estate.getKaptAddr())
                .location(EstateLocationResponse.from(estate))
                .pricePerSqm(estate.getPricePerSquareMeter())
                .area(EstateAreaResponse.from(estate))
                .facility(EstateFacilityResponse.from(estate))
                .build();
    }

    public static List<EstateDetailResponse> from(List<Estate> estates) {
        return estates.stream()
                .map(EstateDetailResponse::from)
                .toList();

    }
}
