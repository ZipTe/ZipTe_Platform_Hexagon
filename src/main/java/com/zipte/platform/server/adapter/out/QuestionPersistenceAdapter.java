package com.zipte.platform.server.adapter.out;

import com.zipte.platform.server.adapter.out.jpa.community.QuestionJpaEntity;
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

        var entity = QuestionJpaEntity.from(question);

        return repository.save(entity)
                .toDomain();
    }

    @Override
    public Optional<Question> loadQuestion(Long id) {
        return repository.findById(id)
                .map(QuestionJpaEntity::toDomain);
    }

    @Override
    public Page<Question> loadQuestionsByKaptCode(String kaptCode, Pageable pageable) {
        return repository.findByKaptCode(kaptCode, pageable)
                .map(QuestionJpaEntity::toDomain);
    }

    @Override
    public void deleteQuestionById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean checkExistQuestion(Long questionId) {
        return repository.existsById(questionId);
    }

}
