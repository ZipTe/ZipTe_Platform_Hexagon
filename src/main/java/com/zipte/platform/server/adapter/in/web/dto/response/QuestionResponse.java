package com.zipte.platform.server.adapter.in.web.dto.response;

import com.zipte.platform.server.domain.community.Question;
import lombok.Builder;

import java.util.*;

@Builder
public record QuestionResponse
        (Long id, String kaptCode, String title, String content) {

    /// 정적 팩토리 메서드
    public static QuestionResponse from(Question question) {
        return QuestionResponse.builder()
                .id(question.getId())
                .title(question.getTitle())
                .kaptCode(question.getKaptCode())
                .content(question.getContent())
                .build();
    }

    public static List<QuestionResponse> from(List<Question> questions) {

        return questions.stream()
                .map(QuestionResponse::from)
                .toList();
    }

}
