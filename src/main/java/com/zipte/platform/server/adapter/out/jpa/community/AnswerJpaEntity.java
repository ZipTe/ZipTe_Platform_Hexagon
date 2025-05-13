package com.zipte.platform.server.adapter.out.jpa.community;

import com.zipte.platform.server.adapter.out.jpa.BaseEntity;
import com.zipte.platform.server.domain.community.Answer;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class AnswerJpaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long questionId;

    private String content;

    /// 정적 팩토리 메서드
    public static AnswerJpaEntity from(Answer answer) {
        return AnswerJpaEntity.builder()
                .userId(answer.getUserId())
                .questionId(answer.getQuestionId())
                .content(answer.getContent())
                .build();
    }

    /// toDomain
    public Answer toDomain() {
        return Answer.builder()
                .id(id)
                .userId(userId)
                .questionId(questionId)
                .content(content)
                .build();
    }

}
