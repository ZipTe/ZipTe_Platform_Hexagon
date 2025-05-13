package com.zipte.platform.server.adapter.out;

import com.zipte.platform.server.adapter.out.jpa.community.AnswerJpaRepository;
import com.zipte.platform.server.application.out.community.AnswerPort;
import com.zipte.platform.server.domain.community.Answer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AnswerPersistenceAdapter implements AnswerPort {

    private final AnswerJpaRepository repository;


    @Override
    public Answer saveAnswer(Answer answer) {
        return null;
    }

    // 답변 조회하기
    @Override
    public Optional<Answer> loadAnswerById(String id) {
        return Optional.empty();
    }

    @Override
    public void deleteAnswerById(Long id) {

    }
}
