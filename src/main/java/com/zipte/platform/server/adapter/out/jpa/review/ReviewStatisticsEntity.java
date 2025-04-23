package com.zipte.platform.server.adapter.out.jpa.review;

import com.zipte.platform.server.domain.review.ReviewStatistics;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ReviewStatisticsEntity {

    private long viewCount;
    private long likeCount;

    // from
    public static ReviewStatisticsEntity from(ReviewStatistics statistics) {
        return ReviewStatisticsEntity.builder()
                .viewCount(statistics.getViewCount())
                .likeCount(statistics.getLikeCount())
                .build();
    }

    // toDomain
    public ReviewStatistics toDomain() {
        return ReviewStatistics.builder()
                .viewCount(viewCount)
                .likeCount(likeCount)
                .build();
    }
}
