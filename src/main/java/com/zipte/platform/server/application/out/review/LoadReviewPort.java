package com.zipte.platform.server.application.out.review;

import com.zipte.platform.server.domain.review.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface LoadReviewPort {

    // 리뷰 가져오기
    Optional<Review> loadReview(Long id);

    // 유저별 작성한 리뷰 가져오기
    Page<Review> getReviewsByMember(Long memberId, Pageable pageable);

    // 최신순 리뷰 가져오기
    Page<Review> getReviews(String aptId, Pageable pageable);

    // 높은 평점순 리뷰 가져오기
    Page<Review> getReviewsByRating(String aptId, Pageable pageable);
}
