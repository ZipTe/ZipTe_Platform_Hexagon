package com.zipte.platform.server.domain.review;

public class ReviewStatisticsFixtures {
    public static ReviewStatistics stub() {
        return ReviewStatisticsFixtures.stub(100, 5);
    }

    public static ReviewStatistics stub(int viewCount, int likesCount) {
        return ReviewStatistics.builder()
                .viewCount(viewCount)
                .likeCount(likesCount)
                .build();
    }
}
