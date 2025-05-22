package com.zipte.platform.server.domain.qa;

import com.zipte.platform.server.adapter.in.web.dto.request.QuestionRequest;

public class QuestionRequestFixtures {

    public static QuestionRequest stub(Long userId) {
        return QuestionRequest.builder()
                .userId(userId)
                .kaptCode("testCode")
                .content("testContent")
                .title("testTitle")
                .build();
    }

    public static QuestionRequest kaptCodeNull(Long userId) {
        return QuestionRequest.builder()
                .userId(userId)
                .kaptCode(null)  // NotNull 위반
                .title("문의")
                .content("내용")
                .build();
    }

    public static QuestionRequest titleNull(Long userId) {
        return QuestionRequest.builder()
                .userId(userId)
                .kaptCode("testCode")
                .title(null)  // NotNull 위반
                .content("내용")
                .build();
    }

    public static QuestionRequest contentNull(Long userId) {
        return QuestionRequest.builder()
                .userId(userId)
                .kaptCode("testCode")
                .title("제목")
                .content(null)  // NotNull 위반
                .build();
    }

    public static QuestionRequest contentTooLong(Long userId) {
        return QuestionRequest.builder()
                .userId(userId)
                .kaptCode("testCode")
                .title("제목")
                .content("a".repeat(1001))  // Size(max = 1000) 위반
                .build();
    }


}
