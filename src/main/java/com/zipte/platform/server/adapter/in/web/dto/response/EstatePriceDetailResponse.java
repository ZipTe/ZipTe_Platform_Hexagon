package com.zipte.platform.server.adapter.in.web.dto.response;

import com.zipte.platform.server.domain.estate.EstatePrice;
import lombok.Builder;

import java.util.Optional;

@Builder
public record EstatePriceDetailResponse(
        double exclusiveArea, String price, String transactionDate) {

    public static EstatePriceDetailResponse from(Optional<EstatePrice> estatePrice) {
        if (estatePrice.isEmpty()) {
            return EstatePriceDetailResponse.builder()
                    .exclusiveArea(0.0)
                    .price("없음")
                    .transactionDate("없음")
                    .build();
        }

        EstatePrice price = estatePrice.get();
        return EstatePriceDetailResponse.builder()
                .exclusiveArea(price.getExclusiveArea() != 0.0 ? price.getExclusiveArea() : 0.0)
                .price(price.getPrice() != null ? price.getPrice() : "없음")
                .transactionDate(price.getTransactionDate() != null ? price.getTransactionDate() : "없음")
                .build();
    }

}
