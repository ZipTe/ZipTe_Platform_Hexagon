package com.zipte.platform.server.adapter.in.web.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zipte.platform.server.domain.estate.Estate;
import com.zipte.platform.server.domain.estate.EstatePrice;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)  // NULL 값인 필드는 JSON 응답에서 제외
public class EstateListResponse {

    // 아파트 기본 정보
    private String complexCode;  // 아파트 단지 코드
    private String complexName;  // 아파트 단지명
    private String address;      // 주소

    // 매물 개수
    private Integer propertyCount;

    // 최근 거래 조회
    private EstatePriceDetailResponse price;

    // 좌표
    private EstateLocationResponse location;


    public static EstateListResponse from(Estate estate) {
        return EstateListResponse.builder()
                .complexCode(estate.getKaptCode())
                .complexName(estate.getKaptName())
                .address(estate.getKaptAddr())
                .location(EstateLocationResponse.from(estate))
                .build();
    }

    public static EstateListResponse from(Estate estate, int count) {
        return EstateListResponse.builder()
                .complexCode(estate.getKaptCode())
                .complexName(estate.getKaptName())
                .address(estate.getKaptAddr())
                .propertyCount(count > 0 ? count : null) // count가 0이면 JSON에서 제외
                .location(EstateLocationResponse.from(estate))
                .build();
    }

    /// 아파트 정보와 가격을 동시에 보여주는 로직
    public static EstateListResponse from(Estate estate, Optional<EstatePrice> price) {
        return EstateListResponse.builder()
                .complexCode(estate.getKaptCode())
                .complexName(estate.getKaptName())
                .address(estate.getKaptAddr())
                .price(EstatePriceDetailResponse.from(price))
                .location(EstateLocationResponse.from(estate))
                .build();
    }

    public static List<EstateListResponse> from(List<Estate> estates) {
        return estates.stream()
                .map(EstateListResponse::from)
                .toList();
    }



}
