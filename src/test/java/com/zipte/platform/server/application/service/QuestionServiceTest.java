package com.zipte.platform.server.application.service;

import com.zipte.platform.server.adapter.in.web.dto.request.QuestionRequest;
import com.zipte.platform.server.application.out.community.AnswerPort;
import com.zipte.platform.server.application.out.community.QuestionPort;
import com.zipte.platform.server.application.out.estate.LoadEstatePort;
import com.zipte.platform.server.application.out.user.UserPort;
import com.zipte.platform.server.domain.qa.AnswerFixtures;
import com.zipte.platform.server.domain.qa.QuestionFixtures;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @Mock
    private QuestionPort questionPort;

    @Mock
    private UserPort userPort;

    @Mock
    private LoadEstatePort loadEstatePort;

    @Mock
    private AnswerPort answerPort;

    @InjectMocks
    private QuestionService sut;


    @Nested()
    @DisplayName("생성 테스트")
    class CreateQuestion {

        @Test
        @DisplayName("[happy] 질문 정상 생성")
        void createQuestion() {

            // Given
            Long userId = 1L;
            String kaptCode = "kaptCode";
            String title = "title";
            String content = "content";

            var request = QuestionRequest.builder()
                    .userId(userId)
                    .kaptCode(kaptCode)
                    .title(title)
                    .content(content)
                    .build();

            given(userPort.checkExistingById(userId))
                    .willReturn(true);

            given(loadEstatePort.checkExistingByCode(kaptCode))
                    .willReturn(true);

            given(questionPort.save(any()))
                    .willReturn(QuestionFixtures.stub(userId, kaptCode, title, content));

            // When
            var result = sut.createQuestion(request);

            // Then
            then(result)
                    .isNotNull()
                    .hasFieldOrPropertyWithValue("userId", userId)
                    .hasFieldOrPropertyWithValue("kaptCode", kaptCode)
                    .hasFieldOrPropertyWithValue("title", title)
                    .hasFieldOrPropertyWithValue("content", content);

        }


        @Test
        @DisplayName("[error] 질문을 남긴 아파트가 없다면 조회 실패")
        void createQuestion_error_estate() {

            // Given
            Long userId = 1L;
            String kaptCode = "kaptCode";
            String title = "title";
            String content = "content";

            var request = QuestionRequest.builder()
                    .userId(userId)
                    .kaptCode(kaptCode)
                    .title(title)
                    .content(content)
                    .build();

            given(userPort.checkExistingById(userId))
                    .willReturn(true);

            given(loadEstatePort.checkExistingByCode(kaptCode))
                    .willReturn(false);

            // When & Then
            Assert.assertThrows(NoSuchElementException.class,
                    () -> sut.createQuestion(request));

        }

        @Test
        @DisplayName("[error] 유저가 없다면 조회 실패")
        void createQuestion_error_user() {

            // Given
            Long userId = 1L;
            String kaptCode = "kaptCode";
            String title = "title";
            String content = "content";

            var request = QuestionRequest.builder()
                    .userId(userId)
                    .kaptCode(kaptCode)
                    .title(title)
                    .content(content)
                    .build();

            given(userPort.checkExistingById(userId))
                    .willReturn(false);

            // When & Then
            Assert.assertThrows(NoSuchElementException.class,
                    () -> sut.createQuestion(request));

        }
    }

    @Nested()
    @DisplayName("조회 테스트")
    class GetQuestion {

        @Test
        @DisplayName("[happy] 답변이 존재하는 경우, 질문 정상 조회")
        void loadQuestion_answer() {

            // Given
            Long questionId = 1L;

            given(questionPort.loadQuestion(questionId))
                    .willReturn(Optional.of(QuestionFixtures.stub()));

            given(answerPort.loadAnswerByQuestionId(questionId))
                    .willReturn(AnswerFixtures.stubs(questionId));

            // When
            var result = sut.loadQuestion(questionId);

            // Then
            assertThat(result.question().id())
                    .isEqualTo(questionId);

            assertThat(result.answers()).hasSize(3);
            assertThat(result.answers())
                    .extracting("userId")
                    .containsExactly(1L, 2L, 3L);
        }


        @Test
        @DisplayName("[happy] 답변이 없는 경우, 질문 정상 조회")
        void loadQuestion_no_answer() {

            // Given
            Long questionId = 1L;

            given(questionPort.loadQuestion(questionId))
                    .willReturn(Optional.of(QuestionFixtures.stub()));

            given(answerPort.loadAnswerByQuestionId(questionId))
                    .willReturn(List.of());

            // When
            var result = sut.loadQuestion(questionId);

            // Then
            assertThat(result.question().id())
                    .isEqualTo(questionId);

            assertThat(result.answers()).hasSize(0);

        }

        @Test
        @DisplayName("[error] 존재하지 않는 질문 ID로 조회 시 예외 발생")
        void loadQuestion_error_id() {
            // Given
            Long questionId = 999L;

            given(questionPort.loadQuestion(questionId))
                    .willReturn(Optional.empty());

            // When & Then
            Assert.assertThrows(NoSuchElementException.class,
                    () -> sut.loadQuestion(questionId));

        }
    }

    @Nested()
    @DisplayName("삭제 테스트")
    class DeleteQuestion {

        @Test
        @DisplayName("[happy] 질문 정상 삭제")
        void deleteQuestion_success() {
            // Given
            Long questionId = 1L;
            Long userId = 100L;

            given(userPort.checkExistingById(userId))
                    .willReturn(true);

            given(questionPort.checkExistQuestionByIdAndUserId(questionId, userId))
                    .willReturn(true);

            // When
            sut.deleteQuestion(questionId, userId);

            // Then
            verify(questionPort).deleteQuestionById(questionId);

        }

        @Test
        @DisplayName("[edge] 다른 유저가 삭제 요청을 진행한다.")
        void deleteQuestion_edge() {

            // Given
            Long questionId = 1L;
            Long attackerId = 10L;

            given(userPort.checkExistingById(attackerId))
                    .willReturn(true);

            given(questionPort.checkExistQuestionByIdAndUserId(questionId, attackerId))
                    .willReturn(false);

            // When & Then
            Assertions.assertThrows(IllegalStateException.class,
                    () -> sut.deleteQuestion(questionId, attackerId));

        }


        @Test
        @DisplayName("[error] 유저가 없다면 조회 실패")
        void createQuestion_error_user() {

            // Given
            Long questionId = 1L;
            Long userId = 999L;

            given(userPort.checkExistingById(userId))
                    .willReturn(false);

            // When & Then
            Assert.assertThrows(NoSuchElementException.class,
                    () -> sut.deleteQuestion(questionId, userId));

        }

    }
}
