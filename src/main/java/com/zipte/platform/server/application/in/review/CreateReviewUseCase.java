package com.zipte.platform.server.application.in.review;

import com.zipte.platform.server.adapter.in.web.dto.request.ReviewRequest;
import com.zipte.platform.server.domain.review.Review;

public interface CreateReviewUseCase {

    Review createReview(ReviewRequest reviewRequest);


}
