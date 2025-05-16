package com.zipte.platform.server.adapter.out.jpa.community;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerJpaRepository extends JpaRepository<AnswerJpaEntity, Long> {

    List<AnswerJpaEntity> findByQuestionId(Long questionId);


}
