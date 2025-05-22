package com.zipte.platform.server.domain.qa;

import com.zipte.platform.server.adapter.in.web.dto.request.AnswerRequest;

public class AnswerRequestFixtures {

    public static AnswerRequest stub(Long userId, Long questionId) {
        return AnswerRequest.builder()
                .userId(userId)
                .questionId(questionId)
                .content("정상적인 답변입니다.")
                .build();
    }

    public static AnswerRequest questionIdNull(Long userId) {
        return AnswerRequest.builder()
                .userId(userId)
                .questionId(null)  // NotNull 위반
                .content("답변입니다.")
                .build();
    }

    public static AnswerRequest contentNull(Long userId, Long questionId) {
        return AnswerRequest.builder()
                .userId(userId)
                .questionId(questionId)
                .content(null)  // NotNull 위반
                .build();
    }

}
