package com.zipte.platform.server.adapter.out;

import com.zipte.platform.server.application.out.recommendation.RecommendationPort;
import com.zipte.platform.server.domain.recommendation.Recommendation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RecommendationPersistenceAdapter implements RecommendationPort {


    @Override
    public Recommendation saveRecommendation(Recommendation recommendation) {
        return null;
    }

    @Override
    public Optional<Recommendation> loadById(Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteRecommendation(Long id) {

    }
}
