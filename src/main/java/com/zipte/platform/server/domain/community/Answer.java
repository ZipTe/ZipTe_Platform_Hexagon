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

    /// 정적 팩토리 메서드
    public static Answer of(Long userId, Long questionId, String content) {
        return Answer.builder()
                .userId(userId)
                .questionId(questionId)
                .content(content)
                .build();
    }

}
