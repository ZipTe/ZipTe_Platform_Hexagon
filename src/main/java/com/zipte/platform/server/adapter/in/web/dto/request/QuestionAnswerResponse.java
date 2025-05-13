package com.zipte.platform.server.adapter.in.web.dto.request;

import com.zipte.platform.server.adapter.in.web.dto.response.AnswerResponse;
import com.zipte.platform.server.adapter.in.web.dto.response.QuestionResponse;
import com.zipte.platform.server.domain.community.Answer;
import com.zipte.platform.server.domain.community.Question;
import lombok.Builder;

@Builder
public record QuestionAnswerResponse(
        QuestionResponse question,
        AnswerResponse answer
) {

    /// 정적 팩토리 메서드
    public static QuestionAnswerResponse from(Question question, Answer answer) {
        return QuestionAnswerResponse.builder()
                .question(QuestionResponse.from(question))
                .answer(AnswerResponse.from(answer))
                .build();
    }

}
