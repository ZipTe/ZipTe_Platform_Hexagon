package com.zipte.platform.server.domain.estate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class EstatePrice {

    private String id;

    private String kaptCode;

    private String kaptName;

    private String kaptAddr;

    private int floor;

    // 전용 면적 (㎡)
    private double exclusiveArea;

    // 거래 금액 (만원)
    private String price;

    // 거래 일자
    private String transactionDate;

}

