package com.zipte.platform.server.adapter.in.web.dto.request;

import com.zipte.platform.server.adapter.in.web.dto.response.AnswerResponse;
import com.zipte.platform.server.adapter.in.web.dto.response.QuestionResponse;
import com.zipte.platform.server.domain.community.Answer;
import com.zipte.platform.server.domain.community.Question;
import lombok.Builder;

import java.util.List;

@Builder
public record QuestionAnswerDetailResponse(
        QuestionResponse question,
        List<AnswerResponse> answer
) {

    /****
     * Creates a {@code QuestionAnswerDetailResponse} from a {@code Question} and a list of {@code Answer} objects.
     *
     * Converts the given domain objects into their corresponding response DTOs and assembles them into a detailed response.
     *
     * @param question the question domain object to convert
     * @param answers the list of answer domain objects to convert
     * @return a {@code QuestionAnswerDetailResponse} containing the converted question and answers
     */
    public static QuestionAnswerDetailResponse from(Question question, List<Answer> answers) {
        return QuestionAnswerDetailResponse.builder()
                .question(QuestionResponse.from(question))
                .answer(AnswerResponse.from(answers))
                .build();
    }

}
