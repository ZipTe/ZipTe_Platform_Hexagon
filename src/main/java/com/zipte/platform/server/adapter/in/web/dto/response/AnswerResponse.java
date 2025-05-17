package com.zipte.platform.server.adapter.in.web.dto.response;

import com.zipte.platform.server.domain.community.Answer;
import lombok.Builder;

import java.util.*;
@Builder
public record AnswerResponse
        (Long id, Long userId,String content) {

    /**
     * Creates an {@code AnswerResponse} from the given {@code Answer} domain object.
     *
     * @param answer the domain object to convert
     * @return a new {@code AnswerResponse} with values copied from the provided {@code Answer}
     */
    public static AnswerResponse from(Answer answer) {
        return AnswerResponse.builder()
                .id(answer.getId())
                .userId(answer.getUserId())
                .content(answer.getContent())
                .build();
    }

    /**
     * Converts a list of Answer objects into a list of AnswerResponse instances.
     *
     * @param answers the list of Answer domain objects to convert
     * @return a list of AnswerResponse objects corresponding to the input answers
     */
    public static List<AnswerResponse> from(List<Answer> answers) {
        return answers.stream()
                .map(AnswerResponse::from)
                .toList();
    }

    /**
     * Creates an {@code AnswerResponse} instance with all fields set to {@code null}.
     *
     * @return an {@code AnswerResponse} with {@code id}, {@code userId}, and {@code content} as {@code null}
     */
    public static AnswerResponse from() {
        return AnswerResponse.builder()
                .id(null)
                .userId(null)
                .content(null)
                .build();

    }

}

