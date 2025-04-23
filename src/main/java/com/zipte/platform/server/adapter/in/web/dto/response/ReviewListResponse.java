package com.zipte.platform.server.adapter.in.web.dto.response;

import com.zipte.platform.server.domain.review.Review;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ReviewListResponse {

    // 아파트 정보
    private String aptId;

    // 리뷰 작성자
    private Long author;

    // 리뷰 내용
    private String title;
    private String content;

    // 리뷰 평점
    private int rating;

    private long viewCount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder.Default
    private List<String> uploadFileNames = List.of(); // Immutable 빈 리스트 사용

    public static ReviewListResponse from(Review review) {
        return ReviewListResponse.builder()
                .aptId(review.getKaptCode())
                .author(review.getUserId())
                .title(review.getSnippet().getTitle())
                .content(review.getSnippet().getContent())
                .rating(review.getSnippet().getOverall())
                .viewCount(review.getStatistics().getViewCount())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }

    public static List<ReviewListResponse> from(List<Review> reviews) {
        return reviews.stream()
                .map(ReviewListResponse::from)
                .collect(Collectors.toList());
    }
}
