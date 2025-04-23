package com.zipte.platform.server.application.service;

import com.zipte.platform.core.response.ErrorCode;
import com.zipte.platform.server.adapter.in.web.dto.request.ReviewRequest;
import com.zipte.platform.server.application.in.review.CreateReviewUseCase;
import com.zipte.platform.server.application.in.review.DeleteReviewUseCase;
import com.zipte.platform.server.application.in.review.GetReviewUseCase;
import com.zipte.platform.server.application.out.estateOwnership.EstateOwnerShipPort;
import com.zipte.platform.server.application.out.review.LoadReviewPort;
import com.zipte.platform.server.application.out.review.RemoveReviewPort;
import com.zipte.platform.server.application.out.review.SaveReviewPort;
import com.zipte.platform.server.application.out.user.UserPort;
import com.zipte.platform.server.domain.review.Review;
import com.zipte.platform.server.domain.review.ReviewSnippet;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService implements CreateReviewUseCase, GetReviewUseCase, DeleteReviewUseCase {

    private final LoadReviewPort loadReviewPort;
    private final SaveReviewPort saveReviewPort;
    private final RemoveReviewPort removeReviewPort;

    /// 외부 의존성
    private final UserPort loadUserPort;
    private final EstateOwnerShipPort ownerShipPort;

    @Override
    public Review createReview(ReviewRequest request) {

        /// 유저 예외처리
        boolean checked = loadUserPort.checkExistingById(request.getUserId());
        if (!checked) {
            throw new NoSuchElementException(ErrorCode.NOT_USER.getMessage());
        }

        /// 해당 유저가 실거주인인지 체크
        boolean ownership = ownerShipPort.loadOwnershipByUser(request.getUserId(), request.getKaptCode());

        if (!ownership) {
            throw new IllegalStateException(ErrorCode.BAD_REQUEST_REVIEW.getMessage());
        }

        /// 정보 생성
        ReviewSnippet snippet = ReviewSnippet.of(request.getTitle(), request.getContent(), request.getTransport(), request.getEnvironment(), request.getApartmentManagement(), request.getLivingEnvironment());

        /// 리뷰 생성
        Review result = Review.of(request.getUserId(), request.getKaptCode(), snippet,true);

        return saveReviewPort.saveReview(result);
    }

    @Override
    public Review getReview(Long id) {
        return loadReviewPort.loadReview(id)
                .orElseThrow(() -> new NoSuchElementException(ErrorCode.NOT_REVIEW.getMessage()));
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
