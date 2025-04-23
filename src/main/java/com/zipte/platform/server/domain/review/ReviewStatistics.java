package com.zipte.platform.server.domain.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ReviewStatistics {

    private long viewCount;
    private long likeCount;

    /// 정적팩토리 메서드 : 초기 기본 생성
    public static ReviewStatistics of() {
        return ReviewStatistics.builder()
                .viewCount(0L)
                .likeCount(0L)
                .build();
    }


    // 로직
    public void changeViewCount(long viewCount) {
        this.viewCount = viewCount;
    }


    public void changeLikeCount(long likeCount) {
        this.viewCount = likeCount;
    }
}
