package com.zipte.platform.server.application.service;

import com.zipte.platform.server.adapter.in.web.dto.request.ReviewRequest;
import com.zipte.platform.server.application.in.review.CreateReviewUseCase;
import com.zipte.platform.server.application.in.review.DeleteReviewUseCase;
import com.zipte.platform.server.application.in.review.GetReviewUseCase;
import com.zipte.platform.server.application.out.review.LoadReviewPort;
import com.zipte.platform.server.application.out.review.RemoveReviewPort;
import com.zipte.platform.server.application.out.review.SaveReviewPort;
import com.zipte.platform.server.application.out.user.UserPort;
import com.zipte.platform.server.domain.review.Review;
import com.zipte.platform.server.domain.review.ReviewSnippet;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class ReviewService implements CreateReviewUseCase, GetReviewUseCase, DeleteReviewUseCase {

    private final LoadReviewPort loadReviewPort;
    private final SaveReviewPort saveReviewPort;
    private final RemoveReviewPort removeReviewPort;
    private final UserPort loadUserPort;

    @Override
    public Review createReview(ReviewRequest request) {

        /// 해당 유저가 실거주인인지 체크


        /// 정보 생성
        ReviewSnippet snippet = ReviewSnippet.of(request.getTitle(), request.getContent(), request.getTransport(), request.getEnvironment(), request.getApartmentManagement(), request.getLivingEnvironment());

        /// 리뷰 생성
        Review result = Review.of(request.getUserId(), request.getKaptCode(), snippet,true);

        return saveReviewPort.saveReview(result);
    }

    @Override
    public Review getReview(Long id) {
        Review review = loadReviewPort.loadReview(id)
                .orElseThrow(() -> new NoSuchElementException("리뷰가 존재하지 않습니다."));

        /// 캐시 활용하여 API 호출할 때마다 조회수 증가

        return review;
    }

    // 최신순
    @Override
    public Page<Review> getReviews(String aptId, Pageable pageable) {

        return loadReviewPort.getReviews(aptId, pageable);
    }

    // 높은 점수별
    @Override
    public Page<Review> getReviewsByRating(String aptId, Pageable pageable) {

        return loadReviewPort.getReviewsByRating(aptId, pageable);
    }


    // 유저별
    @Override
    public Page<Review> getReviewsByMember(Long memberId, Pageable pageable) {

        return loadReviewPort.getReviewsByMember(memberId, pageable);
    }


    @Override
    public void removeReview(Review review) {

        removeReviewPort.removeReview(review);

    }


}
