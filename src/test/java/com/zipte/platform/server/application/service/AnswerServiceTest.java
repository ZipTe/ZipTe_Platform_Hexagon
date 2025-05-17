package com.zipte.platform.server.application.service;

import com.zipte.platform.server.adapter.in.web.dto.request.AnswerRequest;
import com.zipte.platform.server.application.out.community.AnswerPort;
import com.zipte.platform.server.application.out.community.QuestionPort;
import com.zipte.platform.server.application.out.user.UserPort;
import com.zipte.platform.server.domain.qa.AnswerFixtures;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AnswerServiceTest {

    @Mock
    private AnswerPort answerPort;

    @Mock
    private UserPort userPort;

    @Mock
    private QuestionPort questionPort;

    @InjectMocks
    private AnswerService sut;


    @Nested
    @DisplayName("생성 관련 테스트")
    class create{

        @Test
        @DisplayName("[happy] 답변 정상 생성")
        void createAnswer_happy() {

            /// Given
            Long userId = 1L;
            Long questionId = 2L;

            var request = AnswerRequest.builder()
                    .questionId(questionId)
                    .userId(userId)
                    .build();

            given(userPort.checkExistingById(userId))
                    .willReturn(true);

            given(questionPort.checkExistQuestion(questionId))
                    .willReturn(true);

            given(answerPort.saveAnswer(any()))
                    .willReturn(AnswerFixtures.stub(userId, questionId));

            /// When
            var result = sut.createAnswer(request);

            /// Then
            then(result)
                    .isNotNull()
                    .hasFieldOrPropertyWithValue("userId", userId)
                    .hasFieldOrPropertyWithValue("questionId", questionId);
        }


        @Test
        @DisplayName("[error] 존재하지 않는 사용자 검증")
        void createAnswer_whenUserNotExist_thenThrowException() {
            // Given
            Long userId = 999L;
            Long questionId = 1L;

            var request = AnswerRequest.builder()
                    .questionId(questionId)
                    .userId(userId)
                    .build();

            given(userPort.checkExistingById(userId))
                    .willReturn(false);

            // When & Then
            Assert.assertThrows(NoSuchElementException.class
                    , () -> sut.createAnswer(request));
        }

        @Test
        @DisplayName("[error] 존재하지 않는 질문 검증")
        void createAnswer_whenQuestionNotExist_thenThrowException() {
            // Given
            Long userId = 1L;
            Long questionId = 999L;

            var request = AnswerRequest.builder()
                    .questionId(questionId)
                    .userId(userId)
                    .build();

            given(userPort.checkExistingById(userId))
                    .willReturn(true);

            given(questionPort.checkExistQuestion(questionId))
                    .willReturn(false);

            // When & Then
            Assert.assertThrows(NoSuchElementException.class
                    , () -> sut.createAnswer(request));
        }
    }

    @Nested
    @DisplayName("삭제 관련 테스트")
    class deleteAnswer{

        @Test
        @DisplayName("[happy] 답변 정상 삭제")
        void deleteAnswer_happy() {
            // Given
            Long answerId = 10L;
            Long userId = 1L;

            given(userPort.checkExistingById(userId))
                    .willReturn(true);

            given(answerPort.checkExistAnswerByIdAndUserId(answerId, userId))
                    .willReturn(true);

            // When
            sut.deleteAnswer(answerId, userId);

            // Then
            verify(answerPort).deleteAnswerById(answerId);
        }

        @Test
        @DisplayName("[edge] 삭제 권한이 없는 사용자 예외")
        void deleteAnswer_whenUserNotOwner_thenThrowException() {
            // Given
            Long id = 1L;
            Long attackerId = 10L;

            given(userPort.checkExistingById(attackerId))
                    .willReturn(true);

            given(answerPort.checkExistAnswerByIdAndUserId(id, attackerId))
                    .willReturn(false);

            // When & Then
            Assert.assertThrows(IllegalStateException.class,
                    () -> sut.deleteAnswer(id, attackerId));
        }

        @Test
        @DisplayName("[error] 존재하지 않는 사용자 예외")
        void deleteAnswer_whenUserNotExist_thenThrowException() {
            // Given
            Long userId = 999L;
            Long answerId = 10L;

            given(userPort.checkExistingById(userId))
                    .willReturn(false);

            // When & Then
            Assert.assertThrows(NoSuchElementException.class,
                    () -> sut.deleteAnswer(answerId, userId));
        }

    }


}
