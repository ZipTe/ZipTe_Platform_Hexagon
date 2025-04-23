package com.zipte.platform.server.application.in.review;

import com.zipte.platform.server.domain.review.Review;

public interface DeleteReviewUseCase {

    /*
       리뷰를 삭제하는 유즈케이스입니다.
     */

    void removeReview(Review review);


}
