package com.zipte.platform.server.domain.recommendation;

import com.zipte.platform.server.adapter.out.external.ai.zipteAi.dto.ZipteResponse;
import com.zipte.platform.server.domain.BaseDomain;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Recommendation extends BaseDomain {

    private Long id;

    private String kaptCode;

    private String kaptName;

    private double recommendationScore;

    private String reason;

    /// 정적 팩토리 메서드 -
    public static Recommendation of(String kaptCode, String kaptName, double recommendationScore, String reason) {
        return Recommendation.builder()
                .kaptCode(kaptCode)
                .kaptName(kaptName)
                .recommendationScore(recommendationScore)
                .reason(reason)
                .build();
    }

    /// 정적 팩토리 메서드 -
    public static Recommendation of(ZipteResponse response) {
        return Recommendation.builder()
                .kaptCode(response.kaptCode())
                .kaptName(response.kaptName())
                .recommendationScore(response.recommendationScore())
                .reason(response.reason())
                .build();
    }



}
