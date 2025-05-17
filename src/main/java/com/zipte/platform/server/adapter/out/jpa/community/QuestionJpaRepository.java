package com.zipte.platform.server.adapter.out.jpa.community;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionJpaRepository extends JpaRepository<QuestionJpaEntity, Long> {

    Page<QuestionJpaEntity> findByKaptCode(String kaptCode, Pageable pageable);

    boolean existsById(Long id);

    boolean existsByIdAndUserId(Long id, Long userId);

}
