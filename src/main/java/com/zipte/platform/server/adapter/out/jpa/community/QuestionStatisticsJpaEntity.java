package com.zipte.platform.server.adapter.out.jpa.community;

import com.zipte.platform.server.domain.community.QuestionStatistics;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
public class QuestionStatisticsJpaEntity {

    private long viewCount;
    private long commentCount;

    /// 정적팩토리 메서드
    public static QuestionStatisticsJpaEntity from(QuestionStatistics questionStatistics) {
        return QuestionStatisticsJpaEntity.builder()
                .viewCount(questionStatistics.getViewCount())
                .commentCount(questionStatistics.getCommentCount())
                .build();
    }

    public QuestionStatistics toDomain() {
        return QuestionStatistics.builder()
                .viewCount(viewCount)
                .commentCount(commentCount)
                .build();
    }

}
