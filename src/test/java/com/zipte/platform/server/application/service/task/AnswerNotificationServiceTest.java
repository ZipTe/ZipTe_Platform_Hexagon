package com.zipte.platform.server.application.service.task;

import com.zipte.platform.server.application.out.community.QuestionPort;
import com.zipte.platform.server.application.out.notification.LoadNotificationPort;
import com.zipte.platform.server.application.out.notification.answer.DeleteAnswerNotificationPort;
import com.zipte.platform.server.application.out.notification.answer.SaveAnswerNotificationPort;
import com.zipte.platform.server.domain.community.Answer;
import com.zipte.platform.server.domain.notification.AnswerNotification;
import com.zipte.platform.server.domain.notification.AnswerNotificationFixtures;
import com.zipte.platform.server.domain.qa.AnswerFixtures;
import com.zipte.platform.server.domain.qa.QuestionFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AnswerNotificationServiceTest {

    @Mock
    private SaveAnswerNotificationPort savePort;

    @Mock
    private LoadNotificationPort loadPort;

    @Mock
    private DeleteAnswerNotificationPort deletePort;

    @Mock
    private QuestionPort questionPort;


    @InjectMocks
    private AnswerNotificationService sut;

    @Nested
    @DisplayName("생성 관련 테스트")
    class CreateTask {

        @Test
        @DisplayName("[happy] 다른 유저의 답변의 경우, 정상적인 알림 생성 됨")
        void create_happy() {

            // Given
            Long questionUserId = 1L;
            Long questionId = 5L;

            Long answerId = 1L;
            Long answerUserId = 100L;

            Answer answer = AnswerFixtures.stub(answerUserId, questionId);

            given(questionPort.loadQuestion(answer.getQuestionId()))
                    .willReturn(Optional.of(QuestionFixtures.stub(questionUserId)));

            given(savePort.saveNotification(any()))
                    .willReturn(AnswerNotificationFixtures.stub(questionUserId, questionId, answerId, answerUserId));

            // When
            sut.createNotification(answer);

            // Then
            then(savePort).should()
                    .saveNotification(any());

        }


        @Test
        @DisplayName("[edge] 질문과 답변이 같은 유저가 작성한 경우, 알림 생성 되지않음 ")
        void create_sameUser() {

            // Given
            Long questionId = 5L;
            Long questionUserId = 1L;

            Long answerUserId = 1L;

            Answer answer = AnswerFixtures.stub(answerUserId, questionId);

            given(questionPort.loadQuestion(answer.getQuestionId()))
                    .willReturn(Optional.of(QuestionFixtures.stub(questionUserId)));
            // When
            sut.createNotification(answer);

            // Then
            then(savePort).shouldHaveNoInteractions();
        }

    }

    @Nested
    @DisplayName("삭제 관련 테스트")
    class DeleteTask {

        @Test
        @DisplayName("알림 삭제 - 정상 케이스")
        void deleteNotification() {
            // Given
            Long answerId = 10L;
            String id = UUID.randomUUID().toString();

            given(loadPort.loadAnswerNotification(answerId))
                    .willReturn(Optional.of(AnswerNotificationFixtures.stub(id)));

            // When
            sut.removeNotification(answerId);

            // Then
            then(deletePort).should().deleteAnswerNotification(id);

        }


    }

}
