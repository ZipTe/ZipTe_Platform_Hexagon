package com.zipte.platform.server.adapter.out.jpa.recommendation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationJpaRepository extends JpaRepository<RecommendationJpaEntity, Long> {
}
