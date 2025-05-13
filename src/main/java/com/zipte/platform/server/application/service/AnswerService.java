package com.zipte.platform.server.application.service;

import com.zipte.platform.server.adapter.in.web.dto.request.AnswerRequest;
import com.zipte.platform.server.application.in.community.AnswerUseCase;
import com.zipte.platform.server.application.out.community.AnswerPort;
import com.zipte.platform.server.domain.community.Answer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AnswerService implements AnswerUseCase {

    private AnswerPort answerPort;

    @Override
    public Answer createComment(AnswerRequest request) {
        return null;
    }
}
