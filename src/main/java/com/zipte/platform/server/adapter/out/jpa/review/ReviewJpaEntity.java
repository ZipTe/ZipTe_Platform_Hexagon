package com.zipte.platform.server.adapter.out.jpa.review;

import com.zipte.platform.server.adapter.out.jpa.BaseEntity;
import com.zipte.platform.server.domain.review.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ReviewJpaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    private Long userId;

    private String kaptCode;

    private String imageUrl;


    @Embedded
    private ReviewSnippetEntity snippet;

    @Embedded
    private ReviewStatisticsEntity statistics;

    boolean certified;

    // from
    public static ReviewJpaEntity from(Review review) {
        return ReviewJpaEntity.builder()
                .userId(review.getUserId())
                .kaptCode(review.getKaptCode())
                .imageUrl(review.getImageUrl())
                .snippet(ReviewSnippetEntity
                        .from(review.getSnippet()))
                .statistics(ReviewStatisticsEntity
                        .from(review.getStatistics()))
                .certified(review.isCertified())
                .build();
    }

    // toDomain -
    public Review toDomain() {
        return Review.builder()
                .id(id)
                .userId(userId)
                .kaptCode(kaptCode)
                .imageUrl(imageUrl)
                .snippet(snippet.toDomain())
                .statistics(statistics.toDomain())
                .certified(certified)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }
}
