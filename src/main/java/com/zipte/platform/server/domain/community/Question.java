package com.zipte.platform.server.domain.community;

import com.zipte.platform.server.domain.BaseDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Question extends BaseDomain {

    private Long id;

    private Long userId;

    private String kaptCode;

    private String title;

    private String content;

    private QuestionStatistics statistics;

    /**
     * Creates a new {@code Question} instance with the specified user ID, kapt code, title, and content.
     *
     * Initializes the {@code statistics} field with a new {@code QuestionStatistics} instance.
     *
     * @param userId   the ID of the user creating the question
     * @param kaptCode the code associated with the question's context
     * @param title    the title of the question
     * @param content  the content of the question
     * @return a new {@code Question} object with the provided details and initialized statistics
     */
    public static Question of(Long userId, String kaptCode, String title, String content) {

        QuestionStatistics statistics = QuestionStatistics.of();

        return Question.builder()
                .userId(userId)
                .kaptCode(kaptCode)
                .title(title)
                .content(content)
                .statistics(statistics)
                .build();
    }

    /// 비즈니스 로직



}
