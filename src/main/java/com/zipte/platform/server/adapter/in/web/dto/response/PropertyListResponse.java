package com.zipte.platform.server.adapter.in.web.dto.response;

import com.zipte.platform.server.domain.property.Property;
import com.zipte.platform.server.domain.property.PropertyType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PropertyListResponse {
    private Long id;
    private String title;
    private PropertyType type;
    private long price;
    private String city;
    private String district;
    private String dong;
    private long viewCount;
    private long likeCount;

    public static PropertyListResponse from(Property property) {

        return PropertyListResponse.builder()
                .id(property.getId())
                .title(property.getSnippet().getAptName())
                .type(property.getType())
                .price(property.getPrice())
                .city(property.getAddress().getCity())
                .district(property.getAddress().getDistrict())
                .dong(property.getAddress().getDong())
                .viewCount(property.getStatistic().getViewCount())
                .likeCount(property.getStatistic().getLikeCount())
                .build();
    }

    public static List<PropertyListResponse> from(List<Property> properties) {
        return properties.stream().map(PropertyListResponse::from).toList();
    }

}
