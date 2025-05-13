package com.zipte.platform.server.adapter.out;

import com.zipte.platform.server.adapter.out.jpa.community.QuestionJpaRepository;
import com.zipte.platform.server.application.out.community.QuestionPort;
import com.zipte.platform.server.domain.community.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class QuestionPersistenceAdapter implements QuestionPort {

    private final QuestionJpaRepository repository;

    @Override
    public Question save(Question question) {
        return null;
    }

    // 상세 조회하기
    @Override
    public Optional<Question> loadQuestion(Long id) {
        return Optional.empty();
    }

    @Override
    public Page<Question> loadQuestionsByKaptCode(String kaptCode, Pageable pageable) {
        return null;
    }

    @Override
    public void deleteQuestionById(Long id) {

    }
}
