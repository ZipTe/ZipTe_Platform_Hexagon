package com.zipte.platform.server.domain.qa;

import com.zipte.platform.server.adapter.out.jpa.community.AnswerJpaEntity;

public class AnswerJpaFixtures {

    public static AnswerJpaEntity stubAnswer() {
        return AnswerJpaEntity.builder()
                .id(1L)
                .userId(123L)
                .questionId(456L)
                .content("Stub 답변 내용")
                .build();
    }

    public static AnswerJpaEntity stubAnswer(Long id, Long userId, Long questionId, String content) {
        return AnswerJpaEntity.builder()
                .id(id)
                .userId(userId)
                .questionId(questionId)
                .content(content)
                .build();
    }
}
