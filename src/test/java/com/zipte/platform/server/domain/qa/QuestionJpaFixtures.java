package com.zipte.platform.server.domain.qa;

import com.zipte.platform.server.adapter.out.jpa.community.QuestionJpaEntity;
import com.zipte.platform.server.adapter.out.jpa.community.QuestionStatisticsJpaEntity;
import com.zipte.platform.server.domain.community.QuestionStatistics;

public class QuestionJpaFixtures {

    public static QuestionJpaEntity create(
            Long id,
            Long userId,
            String kaptCode,
            String title,
            String content
    ) {

        QuestionStatistics stub = QuestionStatisticsFixtures.stub();

        return QuestionJpaEntity.builder()
                .id(id)
                .userId(userId)
                .kaptCode(kaptCode)
                .title(title)
                .content(content)
                .statistics(QuestionStatisticsJpaEntity.from(stub))
                .build();
    }

    public static QuestionJpaEntity stub() {
        return create(1L, 1L, "A10000001", "기본 제목", "기본 내용");
    }

    public static QuestionJpaEntity stub(Long id) {
        return create(id, 1L, "A10000001", "기본 제목", "기본 내용");
    }

    public static QuestionJpaEntity stub(Long id, Long userId, String kaptCode, String title, String content) {
        return create(id, userId, kaptCode, title, content);
    }

}
