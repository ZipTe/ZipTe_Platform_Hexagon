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
public class Answer extends BaseDomain {

    private Long id;

    private Long userId;

    private Long questionId;

    private String content;

    /**
     * Creates a new Answer instance with the specified user ID, question ID, and content.
     *
     * @param userId the ID of the user providing the answer
     * @param questionId the ID of the question being answered
     * @param content the content of the answer
     * @return a new Answer object with the given user ID, question ID, and content
     */
    public static Answer of(Long userId, Long questionId, String content) {
        return Answer.builder()
                .userId(userId)
                .questionId(questionId)
                .content(content)
                .build();
    }

}
