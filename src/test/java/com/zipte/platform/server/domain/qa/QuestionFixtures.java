package com.zipte.platform.server.domain.qa;

import com.zipte.platform.server.domain.community.Question;

public class QuestionFixtures {

    public static Question stub() {

        return Question.builder()
                .id(1L)
                .userId(1L)
                .kaptCode("kaptCode")
                .title("title")
                .content("content")
                .statistics(QuestionStatisticsFixtures.stub())
                .build();


    }

    public static Question stub(Long userId) {

        return Question.builder()
                .id(1L)
                .userId(userId)
                .kaptCode("kaptCode")
                .title("title")
                .content("content")
                .statistics(QuestionStatisticsFixtures.stub())
                .build();


    }

    public static Question stub(Long userId, String kaptCode, String title, String content) {

        return Question.builder()
                .id(1L)
                .userId(userId)
                .kaptCode(kaptCode)
                .title(title)
                .content(content)
                .statistics(QuestionStatisticsFixtures.stub())
                .build();


    }

}
