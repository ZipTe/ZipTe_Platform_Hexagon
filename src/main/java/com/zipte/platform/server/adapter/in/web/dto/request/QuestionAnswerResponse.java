package com.zipte.platform.server.adapter.in.web.dto.request;

import com.zipte.platform.server.adapter.in.web.dto.response.AnswerResponse;
import com.zipte.platform.server.adapter.in.web.dto.response.QuestionResponse;
import com.zipte.platform.server.domain.community.Answer;
import com.zipte.platform.server.domain.community.Question;
import lombok.Builder;

import java.util.List;

@Builder
public record QuestionAnswerResponse(
        QuestionResponse question,
        List<AnswerResponse> answer
) {

    /// 정적 팩토리 메서드
    public static QuestionAnswerResponse from(Question question, List<Answer> answers) {
        return QuestionAnswerResponse.builder()
                .question(QuestionResponse.from(question))
                .answer(AnswerResponse.from(answers))
                .build();
    }

}
