package com.zipte.platform.server.adapter.in.web.dto.response;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zipte.platform.server.domain.region.RegionPrice;
import lombok.Builder;

@Builder
public record RegionPriceResponse(
        @JsonProperty("지역코드")
        String regionCode,

        @JsonProperty("15평 이하")
        String under15,

        @JsonProperty("15~20평")
        String between15and20,

        @JsonProperty("20~30평")
        String between20and30,

        @JsonProperty("30평 이상")
        String upper30
) {
    public static RegionPriceResponse from(RegionPrice regionPrice) {
        return RegionPriceResponse.builder()
                .regionCode(regionPrice.getRegionCode())
                .under15(format(regionPrice.getUnder15()))
                .between15and20(format(regionPrice.getBetween15and20()))
                .between20and30(format(regionPrice.getBetween20and30()))
                .upper30(format(regionPrice.getUpper30()))
                .build();
    }

    private static String format(Double price) {
        if (price == null) return "-";
        return String.format("%.0f만원", price);
    }
}
