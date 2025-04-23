package com.zipte.platform.server.adapter.in.web.dto.response;

import com.zipte.platform.server.domain.property.Property;
import com.zipte.platform.server.domain.property.PropertyType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PropertyDetailResponse {

    private Long id;
    private Long userId;
    private String complexCode;
    private PropertyType type;
    private long price;
    private boolean verified;

    private PropertySnippetResponse snippet;
    private PropertyStatisticsResponse statistics;

    public static PropertyDetailResponse from(Property property) {
        return PropertyDetailResponse.builder()
                .id(property.getId())
                .userId(property.getOwnerId())
                .complexCode(property.getKaptCode())
                .type(property.getType())
                .price(property.getPrice())
                .verified(property.isVerified())
                .snippet(PropertySnippetResponse.from(property.getSnippet(), property.getAddress()))
                .statistics(PropertyStatisticsResponse.from(property.getStatistic()))
                .build();
    }

}
