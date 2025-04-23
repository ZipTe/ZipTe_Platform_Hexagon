package com.zipte.platform.server.adapter.in.web.dto.response;

import com.zipte.platform.server.domain.estate.Estate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EstateAreaResponse {

    // 아파트 면적 (제곱미터 단위)
    private String area135sqm; // 135㎡ 타입 면적
    private String area136sqm; // 136㎡ 타입 면적
    private String area60sqm;  // 60㎡ 타입 면적
    private String area85sqm;  // 85㎡ 타입 면적

    public static EstateAreaResponse from(Estate estate) {
        return EstateAreaResponse.builder()
                .area135sqm(estate.getKaptMparea_135())
                .area136sqm(estate.getKaptMparea_136())
                .area60sqm(estate.getKaptMparea_60())
                .area85sqm(estate.getKaptMparea_85())
                .build();
    }
}
