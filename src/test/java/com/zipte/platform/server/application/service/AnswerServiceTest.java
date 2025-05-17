package com.zipte.platform.server.application.service;

import com.zipte.platform.server.adapter.in.web.dto.request.AnswerRequest;
import com.zipte.platform.server.application.out.community.AnswerPort;
import com.zipte.platform.server.application.out.community.QuestionPort;
import com.zipte.platform.server.application.out.user.UserPort;
import com.zipte.platform.server.domain.qa.AnswerFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

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


    }
}
