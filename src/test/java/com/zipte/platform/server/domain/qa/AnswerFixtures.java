package com.zipte.platform.server.domain.qa;

import com.zipte.platform.server.domain.community.Answer;

import java.util.*;

public class AnswerFixtures {

    public static Answer stub() {
        return Answer.builder()
                .id(1L)
                .userId(1L)
                .questionId(1L)
                .content("test")
                .build();
    }

    public static Answer stub(Long userId, Long questionId) {
        return Answer.builder()
                .id(1L)
                .userId(userId)
                .questionId(questionId)
                .content("test")
                .build();
    }

    public static List<Answer> stubs(Long questionId) {
        return Arrays.asList(
                stub(1L, questionId),
                stub(2L, questionId),
                stub(3L, questionId)
        );
    }


}
