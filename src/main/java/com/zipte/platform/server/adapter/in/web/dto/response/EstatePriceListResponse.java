package com.zipte.platform.server.adapter.in.web.dto.response;

import com.zipte.platform.server.domain.estate.EstatePrice;
import lombok.Builder;

import java.util.*;

@Builder
public record EstatePriceListResponse(String kaptCode, String kaptName,
                                      double exclusiveArea, String price, String transactionDate) {


    public static EstatePriceListResponse from(EstatePrice estatePrice) {
        return EstatePriceListResponse.builder()
                .kaptCode(estatePrice.getKaptCode())
                .kaptName(estatePrice.getKaptName())
                .exclusiveArea(estatePrice.getExclusiveArea())
                .price(estatePrice.getPrice())
                .transactionDate(estatePrice.getTransactionDate())
                .build();
    }

    public static List<EstatePriceListResponse> from(List<EstatePrice> estatePrices) {
        return estatePrices.stream()
                .map(EstatePriceListResponse::from)
                .toList();
    }

}
