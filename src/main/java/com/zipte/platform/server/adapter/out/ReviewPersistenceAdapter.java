package com.zipte.platform.server.adapter.out;

import com.zipte.platform.server.adapter.out.jpa.review.ReviewJpaEntity;
import com.zipte.platform.server.adapter.out.jpa.review.ReviewJpaRepository;
import com.zipte.platform.server.application.out.review.LoadReviewPort;
import com.zipte.platform.server.application.out.review.RemoveReviewPort;
import com.zipte.platform.server.application.out.review.SaveReviewPort;
import com.zipte.platform.server.domain.review.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReviewPersistenceAdapter implements LoadReviewPort, SaveReviewPort, RemoveReviewPort {

    private final ReviewJpaRepository repository;


    @Override

    public Review saveReview(Review review) {
        var entity = ReviewJpaEntity.from(review);

        return repository.save(entity)
                .toDomain();
    }

    @Override
    public Optional<Review> loadReview(Long id) {

        /// REDIS 키 값 상승


        /// 값 주기
        return repository.findById(id)
                .map(ReviewJpaEntity::toDomain);
    }

    @Override
    public Page<Review> getReviewsByMember(Long memberId, Pageable pageable) {
        return repository.findByUserId(memberId, pageable)
                .map(ReviewJpaEntity::toDomain);
    }

    @Override
    public Page<Review> getReviews(String aptId, Pageable pageable) {
        return repository.findByKaptCode(aptId, pageable)
                .map(ReviewJpaEntity::toDomain);
    }

    @Override
    public Page<Review> getReviewsByRating(String aptId, Pageable pageable) {
        return repository.findByKaptCodeOrderBySnippetOverallDesc(aptId, pageable)
                .map(ReviewJpaEntity::toDomain);
    }

    @Override
    public boolean checkReviewByIdAndUserId(Long reviewId, Long userId) {
        return repository.existsByIdAndUserId(reviewId, userId);
    }

    @Override
    public boolean checkReviewByUserIdAndKaptCode(Long reviewId, String kaptCode) {
        return repository.existsByUserIdAndKaptCode(reviewId, kaptCode);
    }

    @Override
    public void removeReview(Long id) {
        repository.deleteById(id);
    }
}
