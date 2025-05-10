package com.zipte.platform.server.domain.review;

import java.time.LocalDateTime;

public class ReviewFixtures {
    public static Review stub(Long id) {
        return Review.builder()
                .id(id)
                .kaptCode("kaptCode")
                .imageUrl("imageUrl")
                .snippet(ReviewSnippetFixtures.stub())
                .statistics(ReviewStatisticsFixtures.stub())
                .certified(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

    }

    public static Review anotherStub(Long id) {
        return Review.builder()
                .id(id)
                .kaptCode("kaptCode")
                .imageUrl("imageUrl")
                .snippet(ReviewSnippetFixtures.stub())
                .statistics(ReviewStatisticsFixtures.stub())
                .certified(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

    }
}
