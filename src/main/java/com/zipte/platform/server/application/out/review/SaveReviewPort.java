package com.zipte.platform.server.application.out.review;

import com.zipte.platform.server.domain.review.Review;

public interface SaveReviewPort {

    Review saveReview(Review review);

}
