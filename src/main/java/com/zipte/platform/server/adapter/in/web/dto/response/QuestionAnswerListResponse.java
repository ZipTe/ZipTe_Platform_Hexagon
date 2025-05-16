package com.zipte.platform.server.adapter.in.web.dto.response;

import com.zipte.platform.server.domain.community.Answer;
import com.zipte.platform.server.domain.community.Question;
import lombok.Builder;

import java.util.Optional;

@Builder
public record QuestionAnswerListResponse(
        QuestionResponse question,
        AnswerResponse answer
) {

    /// 정적 팩토리 메서드
    public static QuestionAnswerListResponse from(Question question, Optional<Answer> answer) {
        return QuestionAnswerListResponse.builder()
                .question(QuestionResponse.from(question))
                .answer(answer
                        .map(AnswerResponse::from)
                        .orElse(AnswerResponse.from()))
                .build();
    }

}
