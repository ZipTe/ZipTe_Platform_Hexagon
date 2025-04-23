package com.zipte.platform.server.adapter.in.web.dto.response;

import com.zipte.platform.server.domain.property.PropertyAddress;
import com.zipte.platform.server.domain.property.PropertySnippet;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PropertySnippetResponse {
    private String complexName;
    private String streetAddress;

    private String detailAddress;
    private String description;

    private int quantity;
    private int bathrooms;
    private int builtYear;

    public static PropertySnippetResponse from(PropertySnippet snippet, PropertyAddress address) {
        return PropertySnippetResponse.builder()
                .complexName(snippet.getAptName())
                .description(snippet.getDescription())
                .streetAddress(address.getStreetAddress())
                .detailAddress(address.getDetailAddress())
                .quantity(snippet.getQuantity())
                .bathrooms(snippet.getBathrooms())
                .builtYear(snippet.getBuiltYear())
                .build();
    }
}
