package com.zipte.platform.server.adapter.in.web.swagger;

import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.core.response.pageable.PageRequest;
import com.zipte.platform.core.response.pageable.PageResponse;
import com.zipte.platform.security.oauth2.domain.PrincipalDetails;
import com.zipte.platform.server.adapter.in.web.dto.request.ReviewRequest;
import com.zipte.platform.server.adapter.in.web.dto.response.ReviewDetailResponse;
import com.zipte.platform.server.adapter.in.web.dto.response.ReviewListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "리뷰 API", description = "리뷰 관련 API")
public interface ReviewApiSpec {

    @Operation(
            summary = "아파트 리뷰 추가",
            description = "JWT 토큰을 기반으로 아파트 코드를 통해 리뷰를 추가합니다.",
            security = @SecurityRequirement(name = "JWT"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(name = "리뷰 등록 예시", value = REVIEW_PAYLOAD),
                            }
                    )
            )
    )
    ApiResponse<ReviewDetailResponse> create(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @Valid @org.springframework.web.bind.annotation.RequestBody ReviewRequest request);


    @Operation(
            summary = "특정 유저의 리뷰 조회",
            description = "조회하고 싶은 유저의 리뷰를 확인할 수 있습니다."
    )
    ApiResponse<PageResponse<ReviewListResponse>> getReviewByMemberId(

            @Parameter(description = "조회할 회원 Id", required = true, example = "1")
            @PathVariable("memberId") Long memberId,
            @ParameterObject PageRequest pageRequest);


    // 특정 아파트 리뷰 조회
    @Operation(
            summary = "특정 아파트 리뷰 조회",
            description = "조회하고 싶은 아파트의 리뷰를 확인할 수 있습니다."
    )
    ApiResponse<PageResponse<ReviewListResponse>> getListByKaptCode(

            @Parameter(description = "조회할 아파트 코드", required = true, example = "A46378823")
            @PathVariable("kaptCode") String kaptCode,

            @ParameterObject PageRequest pageRequest,

            @Parameter(description = "정렬 순서 정의", example = "rating")
            @RequestParam(value = "orderBy", defaultValue = "date") String orderBy);


    // 리뷰 상세 정보
    @Operation(
            summary = "리뷰 상세 조회",
            description = "조회하고 싶은 리뷰를 자세하게 확인할 수 있습니다."
    )
    ApiResponse<ReviewDetailResponse> getReviewsWithComments(

            @Parameter(description = "조회할 리뷰 Id", required = true, example = "1")
            @PathVariable("reviewId") Long reviewId);



    String REVIEW_PAYLOAD = """
            {
              "kaptCode": "A46378823",
              "title": "조용하고 편리한 동네입니다",
              "content": "지하철역과 버스정류장이 가까워 교통이 편리하고, 주변 공원이 잘 되어 있어 만족합니다.",
              "transport": 5,
              "environment": 4,
              "apartmentManagement": 5,
              "livingEnvironment": 4
            }
            """;


}
