package com.zipte.platform.server.adapter.in.web.dto.response;

import com.zipte.platform.server.domain.community.Answer;
import com.zipte.platform.server.domain.community.Question;
import lombok.Builder;

import java.util.List;

@Builder
public record QuestionAnswerDetailResponse(
        QuestionResponse question,
        List<AnswerResponse> answers
) {

    /// 정적 팩토리 메서드
    public static QuestionAnswerDetailResponse from(Question question, List<Answer> answers) {
        return QuestionAnswerDetailResponse.builder()
                .question(QuestionResponse.from(question))
                .answers(AnswerResponse.from(answers))
                .build();
    }

}
