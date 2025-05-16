package com.zipte.platform.server.adapter.in.web.dto.response;

import com.zipte.platform.server.domain.community.Answer;
import lombok.Builder;

import java.util.*;
@Builder
public record AnswerResponse
        (Long id, Long userId,String content) {

    /// 정적 팩토리 메서드
    public static AnswerResponse from(Answer answer) {
        return AnswerResponse.builder()
                .id(answer.getId())
                .userId(answer.getUserId())
                .content(answer.getContent())
                .build();
    }

    public static List<AnswerResponse> from(List<Answer> answers) {
        return answers.stream()
                .map(AnswerResponse::from)
                .toList();
    }

}

