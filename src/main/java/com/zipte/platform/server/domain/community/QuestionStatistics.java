package com.zipte.platform.server.domain.community;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class QuestionStatistics {

    private long viewCount;
    private long commentCount;

    /// 정적팩토리 메서드 : 초기 기본 생성
    public static QuestionStatistics of() {
        return QuestionStatistics.builder()
                .viewCount(0L)
                .commentCount(0L)
                .build();
    }

}
