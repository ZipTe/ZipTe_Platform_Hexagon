package com.zipte.platform.server.application.service;

import com.zipte.platform.core.response.ErrorCode;
import com.zipte.platform.server.adapter.in.web.dto.request.AnswerRequest;
import com.zipte.platform.server.application.in.community.AnswerUseCase;
import com.zipte.platform.server.application.out.community.AnswerPort;
import com.zipte.platform.server.application.out.community.QuestionPort;
import com.zipte.platform.server.application.out.user.UserPort;
import com.zipte.platform.server.domain.community.Answer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class AnswerService implements AnswerUseCase {

    private final AnswerPort answerPort;

    /// 외부 의존성
    private final UserPort userPort;
    private final QuestionPort questionPort;

    /**
     * Creates and saves an answer comment after validating the existence of the user and question.
     *
     * @param request the answer creation request containing user ID, question ID, and content
     * @return the saved Answer entity
     * @throws NoSuchElementException if the user or question does not exist
     */
    @Override
    public Answer createComment(AnswerRequest request) {

        /// 유저 예외처리
        boolean userChecked = userPort.checkExistingById(request.getUserId());
        if (!userChecked) {
            throw new NoSuchElementException(ErrorCode.NOT_USER.getMessage());
        }

        /// 질문 예외처리
        boolean questionChecked = questionPort.checkExistQuestion(request.getQuestionId());
        if (!questionChecked) {
            throw new NoSuchElementException(ErrorCode.NOT_QUESTION.getMessage());
        }

        /// 객체 생성
        Answer answer = Answer.of(request.getUserId(), request.getQuestionId(), request.getContent());

        /// 저장하기
        return answerPort.saveAnswer(answer);
    }
}
