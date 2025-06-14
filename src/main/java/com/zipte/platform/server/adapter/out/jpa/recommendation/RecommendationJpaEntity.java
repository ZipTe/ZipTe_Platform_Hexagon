package com.zipte.platform.server.adapter.out.jpa.recommendation;

import com.zipte.platform.server.adapter.out.jpa.BaseEntity;
import com.zipte.platform.server.domain.recommendation.Recommendation;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecommendationJpaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String kaptCode;

    private String kaptName;

    private double recommendationScore;

    private String reason;

    /// 정적 팩토리 메서드
    public RecommendationJpaEntity from(Recommendation recommendation) {
        return RecommendationJpaEntity.builder()
                .kaptCode(recommendation.getKaptCode())
                .kaptName(recommendation.getKaptName())
                .recommendationScore(recommendation.getRecommendationScore())
                .reason(recommendation.getReason())
                .build();
    }

    /// 도메인 생성
    public Recommendation toDomain(){
        return Recommendation.builder()
                .id(id)
                .kaptCode(kaptCode)
                .kaptName(kaptName)
                .recommendationScore(recommendationScore)
                .reason(reason)
                .build();
    }

}
