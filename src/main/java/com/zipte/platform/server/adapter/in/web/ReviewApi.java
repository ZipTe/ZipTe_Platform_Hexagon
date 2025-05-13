package com.zipte.platform.server.adapter.in.web;

import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.core.response.pageable.PageRequest;
import com.zipte.platform.core.response.pageable.PageResponse;
import com.zipte.platform.security.oauth2.domain.PrincipalDetails;
import com.zipte.platform.server.adapter.in.web.dto.request.ReviewRequest;
import com.zipte.platform.server.adapter.in.web.dto.response.ReviewDetailResponse;
import com.zipte.platform.server.adapter.in.web.dto.response.ReviewListResponse;
import com.zipte.platform.server.adapter.in.web.swagger.ReviewApiSpec;
import com.zipte.platform.server.application.in.review.CreateReviewUseCase;
import com.zipte.platform.server.application.in.review.DeleteReviewUseCase;
import com.zipte.platform.server.application.in.review.GetReviewUseCase;
import com.zipte.platform.server.domain.review.Review;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
@Log4j2
public class ReviewApi implements ReviewApiSpec {

    private final CreateReviewUseCase createService;
    private final GetReviewUseCase getService;
    private final DeleteReviewUseCase removeService;

    // 리뷰 작성
    @PostMapping
    public ApiResponse<ReviewDetailResponse> create(@AuthenticationPrincipal PrincipalDetails principalDetails, @Valid @RequestBody ReviewRequest request) {

        /// 유저 ID 추출해서 사용
        Long userId = principalDetails.getId();
        request.setUserId(userId);

        return ApiResponse.created(ReviewDetailResponse.from(createService.createReview(request)));
    }

    // 특정 회원의 리뷰 조회
    @GetMapping("/list/member/{memberId}")
    public ApiResponse<PageResponse<ReviewListResponse>> getReviewByMemberId(@PathVariable("memberId") Long memberId, PageRequest pageRequest) {

        Pageable pageable = org.springframework.data.domain.PageRequest.of(pageRequest.getPage() - 1, pageRequest.getSize(), Sort.by("id").descending());

        Page<Review> result = getService.getReviewsByMember(memberId, pageable);
        List<ReviewListResponse> dtolist = ReviewListResponse.from(result.getContent());


        return ApiResponse.ok(new PageResponse<>(dtolist, pageRequest, result.getTotalElements()));

    }

    // 특정 아파트 리뷰 조회
    @GetMapping("/list/apt/{kaptCode}")
    public ApiResponse<PageResponse<ReviewListResponse>> getListByKaptCode(
            @PathVariable("kaptCode") String kaptCode,
            PageRequest pageRequest,
            @RequestParam(value = "orderBy", defaultValue = "date") String orderBy) {

        Pageable pageable = org.springframework.data.domain.PageRequest.of(pageRequest.getPage() - 1, pageRequest.getSize(), Sort.by("id").descending());


        switch (orderBy) {
            case "rating":
                Page<Review> result = getService.getReviewsByRating(kaptCode, pageable);
                List<ReviewListResponse> dtolist = ReviewListResponse.from(result.getContent());

                return ApiResponse.ok(new PageResponse<>(dtolist, pageRequest, result.getTotalElements()));

            case "date":
            default:
                Page<Review> result2 = getService.getReviews(kaptCode, pageable);
                List<ReviewListResponse> dtolist2 = ReviewListResponse.from(result2.getContent());

                return ApiResponse.ok(new PageResponse<>(dtolist2, pageRequest, result2.getTotalElements()));

        }
    }

    // 리뷰 상세 정보
    @GetMapping("/{reviewId}")
    public ApiResponse<ReviewDetailResponse> getReviewsWithComments(@PathVariable("reviewId") Long reviewId) {

        Review review = getService.getReview(reviewId);

        return ApiResponse.ok(ReviewDetailResponse.from(review));
    }

}
