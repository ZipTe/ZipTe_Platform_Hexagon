package com.zipte.platform.server.application.service;

import com.zipte.platform.server.adapter.in.web.dto.request.QuestionAnswerResponse;
import com.zipte.platform.server.adapter.in.web.dto.request.QuestionRequest;
import com.zipte.platform.server.application.in.community.QuestionUseCase;
import com.zipte.platform.server.application.out.community.AnswerPort;
import com.zipte.platform.server.application.out.community.QuestionPort;
import com.zipte.platform.server.domain.community.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService implements QuestionUseCase {

    private QuestionPort questionPort;

    /// 외부 의존성
    private AnswerPort answerPort;

    @Override
    public Question createQuestion(QuestionRequest request) {
        return null;
    }

    @Override
    public QuestionAnswerResponse loadQuestion(Long questionId) {
        return null;
    }


}
