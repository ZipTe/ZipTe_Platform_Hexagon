package com.zipte.platform.server.application.service;

import com.zipte.platform.core.response.ErrorCode;
import com.zipte.platform.server.adapter.in.web.dto.request.ReviewRequest;
import com.zipte.platform.server.application.out.estate.LoadEstatePort;
import com.zipte.platform.server.application.out.estateOwnership.EstateOwnerShipPort;
import com.zipte.platform.server.application.out.review.LoadReviewPort;
import com.zipte.platform.server.application.out.review.RemoveReviewPort;
import com.zipte.platform.server.application.out.review.SaveReviewPort;
import com.zipte.platform.server.application.out.user.UserPort;
import com.zipte.platform.server.domain.review.Review;
import com.zipte.platform.server.domain.review.ReviewFixtures;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.NoSuchElementException;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Service] 리뷰 서비스 단위 테스트")
class ReviewServiceTest {

    @Mock
    private SaveReviewPort saveReviewPort;
    @Mock
    private LoadReviewPort loadReviewPort;
    @Mock
    private RemoveReviewPort removeReviewPort;
    @Mock
    private EstateOwnerShipPort ownershipPort;
    @Mock
    private UserPort loadUserPort;
    @Mock
    private LoadEstatePort loadEstatePort;

    @InjectMocks
    private ReviewService sut;


    @Nested()
    @DisplayName("리뷰 생성 관련 테스트")
    class SaveReview {


        @Test
        @DisplayName("[happy] 실거주민이 유효한 별점을 남긴다")
        void createReview() {

            // Given
            var reviewRequest = ReviewRequest.builder()
                    .userId(1L)
                    .kaptCode("kaptCode")
                    .title("title")
                    .content("content")
                    .apartmentManagement(1)
                    .environment(1)
                    .livingEnvironment(1)
                    .transport(1)
                    .build();

            Review review = ReviewFixtures.stub(1L);
            given(loadUserPort.checkExistingById(1L)).willReturn(true);
            given(ownershipPort.loadOwnershipByUser(any(), any())).willReturn(true);
            given(loadEstatePort.checkExistingByCode(any())).willReturn(true);

            given(saveReviewPort.saveReview(any())).willReturn(review);

            // When
            var result = sut.createReview(reviewRequest);

            // Then
            then(result)
                    .isNotNull()
                    .hasFieldOrPropertyWithValue("id", review.getId())
                    .hasFieldOrPropertyWithValue("kaptCode", review.getKaptCode())
                    .hasFieldOrPropertyWithValue("snippet.title", review.getSnippet().getTitle())
                    .hasFieldOrPropertyWithValue("snippet.content", review.getSnippet().getContent())
                    .hasFieldOrPropertyWithValue("snippet.apartmentManagement", review.getSnippet().getApartmentManagement())
                    .hasFieldOrPropertyWithValue("statistics.viewCount", review.getStatistics().getViewCount());

        }

        @Test
        @DisplayName("[edge] 동일 유저가 동일 아파트에 이미 리뷰를 작성한 경우 예외 발생")
        void createReviewWhenAlreadyExists() {
            // Given
            Long userId = 1L;

            var reviewRequest = ReviewRequest.builder()
                    .userId(userId)
                    .kaptCode("kaptCode")
                    .title("중복리뷰")
                    .content("이미 작성한 리뷰입니다")
                    .apartmentManagement(2)
                    .environment(2)
                    .livingEnvironment(2)
                    .transport(2)
                    .build();

            given(loadUserPort.checkExistingById(userId)).willReturn(true);

            // 이미 작성된 리뷰가 있다고 가정
            given(loadReviewPort.checkReviewByUserIdAndKaptCode(userId, "kaptCode"))
                    .willReturn(true);


            // When & Then
            Assertions.assertThatThrownBy(() -> sut.createReview(reviewRequest))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage(ErrorCode.BAD_REQUEST_DUPLICATE_REVIEW.getMessage());

        }


        @Test
        @DisplayName("[edge] 실거주민이 0점을 남긴다")
        void createReviewInRating0() {

            // Given
            var reviewRequest = ReviewRequest.builder()
                    .userId(1L)
                    .kaptCode("kaptCode")
                    .title("title")
                    .content("content")
                    .apartmentManagement(0)
                    .environment(1)
                    .livingEnvironment(0)
                    .transport(1)
                    .build();

            // When
            given(loadUserPort.checkExistingById(1L)).willReturn(true);
            given(loadEstatePort.checkExistingByCode(any())).willReturn(true);
            given(ownershipPort.loadOwnershipByUser(any(), any())).willReturn(true);


            // Then
            Assertions.assertThatThrownBy(() -> sut.createReview(reviewRequest))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("[edge] 리뷰 총점이 0.5로 너무 낮은 경우 예외 발생")
        void createReviewInRating_Under1() {

            // Given
            var reviewRequest = ReviewRequest.builder()
                    .userId(1L)
                    .kaptCode("kaptCode")
                    .title("title")
                    .content("content")
                    .apartmentManagement(0)
                    .environment(1)
                    .livingEnvironment(0)
                    .transport(1)
                    .build();

            // When
            given(loadUserPort.checkExistingById(1L)).willReturn(true);
            given(loadEstatePort.checkExistingByCode(any())).willReturn(true);
            given(ownershipPort.loadOwnershipByUser(any(), any())).willReturn(true);

            // Then
            Assertions.assertThatThrownBy(() -> sut.createReview(reviewRequest))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("[edge] 비거주민이 별점을 남기려 한다.")
        void createReviewNoUserId() {

            // Given
            var reviewRequest = ReviewRequest.builder()
                    .userId(1L)
                    .kaptCode("kaptCode")
                    .title("title")
                    .content("content")
                    .apartmentManagement(0)
                    .environment(1)
                    .livingEnvironment(0)
                    .transport(1)
                    .build();

            given(loadUserPort.checkExistingById(1L)).willReturn(true);
            given(loadEstatePort.checkExistingByCode(any())).willReturn(true);
            given(ownershipPort.loadOwnershipByUser(any(), any())).willReturn(false); // 유저가 가지고 있지 않다고 가정

            // When & Then
            Assertions.assertThatThrownBy(() -> sut.createReview(reviewRequest))
                    .isInstanceOf(IllegalStateException.class);
        }

        @Test
        @DisplayName("[error] 존재하지않는 아파트에 리뷰를 남기려 한다.")
        void createReviewNoKaptCode() {

            // Given
            var reviewRequest = ReviewRequest.builder()
                    .userId(1L)
                    .kaptCode("kaptCode")
                    .title("title")
                    .content("content")
                    .apartmentManagement(0)
                    .environment(1)
                    .livingEnvironment(0)
                    .transport(1)
                    .build();

            given(loadUserPort.checkExistingById(1L)).willReturn(true);
            given(loadEstatePort.checkExistingByCode(any())).willReturn(false);

            // When & Then
            Assertions.assertThatThrownBy(() -> sut.createReview(reviewRequest))
                    .isInstanceOf(NoSuchElementException.class);

        }


        // Error
        @Test
        @DisplayName("[error] 존재하지 않는 유저가 리뷰를 남기려는 경우")
        void createReviewError_user() {

            // Given

            Long userId = 1L;
            var reviewRequest = ReviewRequest.builder()
                    .userId(userId)
                    .kaptCode("kaptCode")
                    .title("title")
                    .content("content")
                    .apartmentManagement(0)
                    .environment(1)
                    .livingEnvironment(0)
                    .transport(1)
                    .build();

            // When
            given(loadUserPort.checkExistingById(userId))
                    .willReturn(false);


            // Then
            Assertions.assertThatThrownBy(() -> sut.createReview(reviewRequest))
                    .isInstanceOf(NoSuchElementException.class);
        }
    }


    @Nested()
    @DisplayName("리뷰 조회 관련 테스트")
    class LoadReview {
        /// 조회
        @Test
        @DisplayName(("[happy] 아파트에 대한 리뷰가 있는 경우, 정상 조회된다."))
        void loadReviewByCode_happy() {

            // Given
            String kaptCode = "kaptCode";
            Pageable pageable = PageRequest.of(0, 10);

            Page<Review> stubs = ReviewFixtures.pagedStubs(pageable);

            given(loadEstatePort.checkExistingByCode(kaptCode))
                    .willReturn(true);
            given(loadReviewPort.getReviews(kaptCode, pageable))
                    .willReturn(stubs);

            // When
            Page<Review> reviews = sut.getReviews(kaptCode, pageable);

            // Then
            BDDAssertions.then(reviews.getContent())
                    .hasSize(2);
        }

        @Test
        @DisplayName(("[edge] 아파트에 대한 리뷰가 없는 경우, 0개의 리뷰가 조회된다.."))
        void loadReviewByCode_bad() {

            // Given
            String kaptCode = "kaptCode";
            Pageable pageable = PageRequest.of(0, 10);

            given(loadEstatePort.checkExistingByCode(kaptCode)).willReturn(true);
            given(loadReviewPort.getReviews(kaptCode, pageable))
                    .willReturn(Page.empty());
            // When
            Page<Review> reviews = sut.getReviews(kaptCode, pageable);

            // Then
            BDDAssertions.then(reviews.getContent())
                    .hasSize(0);
        }

        @Test
        @DisplayName("[error] 존재하지 않는 아파트 코드로 조회하면 예외 발생")
        void loadReviewByCode_error() {

            // Given
            String kaptCode = "kaptCode";
            Pageable pageable = PageRequest.of(0, 10);

            // When
            given(loadEstatePort.checkExistingByCode(kaptCode))
                    .willReturn(false);

            // Then
            Assertions.assertThatThrownBy(() -> sut.getReviews(kaptCode, pageable))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage(ErrorCode.NOT_ESTATE.getMessage());


        }
    }

    @Nested()
    @DisplayName("리뷰 삭제 관련 테스트")
    class RemoveReview {
        /// 삭제
        @Test
        @DisplayName("[happy] 리뷰의 id와 작성한 유저의 id가 동일하다면, 리뷰를 삭제할 수 있다.")
        void deleteReviews_happy() {

            // Given
            Long id = 3L;
            Long userId = 1L;

            given(loadUserPort.checkExistingById(userId))
                    .willReturn(true);

            given(loadReviewPort.checkReviewByIdAndUserId(id, userId))
                    .willReturn(true);

            // When
            sut.removeReview(id, userId);

            // Then
            verify(removeReviewPort).removeReview(id);

        }

        @Test
        @DisplayName("[edge] 리뷰의 id와 작성한 유저의 id가 동일하지 않을 때, 리뷰를 삭제할 수 없다.")
        void deleteReviews_edge() {

            // Given
            Long id = 3L;
            Long attackerId = 1L;

            given(loadUserPort.checkExistingById(attackerId))
                    .willReturn(true);

            given(loadReviewPort.checkReviewByIdAndUserId(id, attackerId))
                    .willReturn(false);

            // When & Then
            Assertions.assertThatThrownBy(() -> sut.removeReview(id, attackerId))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage(ErrorCode.BAD_REQUEST_DELETE_REVIEW.getMessage());


        }


        @Test
        @DisplayName("[error] 존재하지 않는 유저가 삭제 시도")
        void deleteReviews_error() {

            // Given
            Long id = 3L;
            Long userId = 10L;

            given(loadUserPort.checkExistingById(userId))
                    .willReturn(false);

            // When & Then
            Assertions.assertThatThrownBy(() -> sut.removeReview(id, userId))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage(ErrorCode.NOT_USER.getMessage());

        }
    }

}
