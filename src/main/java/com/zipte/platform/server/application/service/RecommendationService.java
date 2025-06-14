package com.zipte.platform.server.application.service;

import com.zipte.platform.server.adapter.out.external.ai.zipteAi.dto.ZipteResponse;
import com.zipte.platform.server.application.in.recommendation.RecommendationUseCase;
import com.zipte.platform.server.application.out.external.ai.RecommendAIPort;
import com.zipte.platform.server.application.out.recommendation.RecommendationPort;
import com.zipte.platform.server.domain.recommendation.Recommendation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationService implements RecommendationUseCase {

    private final RecommendationPort recommendationPort;

    /// 외부 의존성
    private final RecommendAIPort AIPort;


    @Override
    public List<Recommendation> recommendations(Long userId) {

        /// AI에게 요청하기
        List<ZipteResponse> recommended = AIPort.recommend(userId);

        /// 스냅샷 남기기


        return List.of();
    }
}
