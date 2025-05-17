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

    /**
     * Creates a {@code QuestionAnswerListResponse} from a {@code Question} and an optional {@code Answer}.
     *
     * If the {@code Answer} is present, it is converted to an {@code AnswerResponse}; otherwise, a default {@code AnswerResponse} is used.
     *
     * @param question the question to include in the response
     * @param answer an optional answer associated with the question
     * @return a {@code QuestionAnswerListResponse} containing the question and corresponding answer response
     */
    public static QuestionAnswerListResponse from(Question question, Optional<Answer> answer) {
        return QuestionAnswerListResponse.builder()
                .question(QuestionResponse.from(question))
                .answer(answer
                        .map(AnswerResponse::from)
                        .orElse(AnswerResponse.from()))
                .build();
    }

}
