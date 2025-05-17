package com.zipte.platform.server.adapter.in.web.dto.response;

import com.zipte.platform.server.domain.community.Question;
import lombok.Builder;

@Builder
public record QuestionResponse
        (Long id, String kaptCode, String title, String content) {

    /**
     * Creates a {@code QuestionResponse} from a {@code Question} domain object.
     *
     * @param question the source {@code Question} entity
     * @return a {@code QuestionResponse} containing the id, title, kaptCode, and content from the given {@code Question}
     */
    public static QuestionResponse from(Question question) {
        return QuestionResponse.builder()
                .id(question.getId())
                .title(question.getTitle())
                .kaptCode(question.getKaptCode())
                .content(question.getContent())
                .build();
    }

}
