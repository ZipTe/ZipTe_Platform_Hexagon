package com.zipte.platform.server.adapter.out.jpa.community;

import com.zipte.platform.server.adapter.out.jpa.BaseEntity;
import com.zipte.platform.server.domain.community.Question;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class QuestionJpaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String kaptCode;

    private String title;

    private String content;

    @Embedded
    private QuestionStatisticsJpaEntity statistics;

    /// 정적 팩토리 메서드
    public static QuestionJpaEntity from(Question question) {
        return QuestionJpaEntity.builder()
                .userId(question.getUserId())
                .kaptCode(question.getKaptCode())
                .title(question.getTitle())
                .content(question.getContent())
                .statistics(QuestionStatisticsJpaEntity.from(question.getStatistics()))
                .build();
    }

    public Question toDomain(){
        return Question.builder()
                .id(id)
                .userId(userId)
                .kaptCode(kaptCode)
                .title(title)
                .content(content)
                .statistics(statistics.toDomain())
                .build();

    }
}
