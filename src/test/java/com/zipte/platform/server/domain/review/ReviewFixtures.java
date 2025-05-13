package com.zipte.platform.server.domain.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

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

    public static Page<Review> pagedStubs(Pageable pageable) {
        List<Review> reviews = List.of(
                stub(1L),
                anotherStub(2L)
        );
        return new PageImpl<>(reviews, pageable, reviews.size());
    }
}

