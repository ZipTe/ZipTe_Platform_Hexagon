package com.zipte.platform.server.application.out.recommendation;

import com.zipte.platform.server.domain.recommendation.Recommendation;

import java.util.Optional;

public interface RecommendationPort {

    /// 저장하기
    Recommendation saveRecommendation(Recommendation recommendation);

    /// 조회하기
    Optional<Recommendation> loadById(Long id);

    /// 삭제하기
    void deleteRecommendation(Long id);

}
