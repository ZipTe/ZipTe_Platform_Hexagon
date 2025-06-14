package com.zipte.platform.server.application.in.recommendation;

import com.zipte.platform.server.domain.recommendation.Recommendation;

import java.util.List;

public interface RecommendationUseCase {

    /*
        아파트 추천 시스템 가동하기
     */

    /// 유저의 아이디를 바탕으로 진행한다.
    List<Recommendation> recommendations(Long userId);

}
