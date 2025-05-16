package com.zipte.platform.server.adapter.out;

import com.zipte.platform.server.adapter.out.jpa.community.AnswerJpaEntity;
import com.zipte.platform.server.adapter.out.jpa.community.AnswerJpaRepository;
import com.zipte.platform.server.application.out.community.AnswerPort;
import com.zipte.platform.server.domain.community.Answer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AnswerPersistenceAdapter implements AnswerPort {

    private final AnswerJpaRepository repository;

    @Override
    public Answer saveAnswer(Answer answer) {
        var entity = AnswerJpaEntity.from(answer);

        return repository.save(entity)
                .toDomain();
    }

    @Override
    public Optional<Answer> loadAnswerById(Long id) {
        return repository.findById(id)
                .map(AnswerJpaEntity::toDomain);
    }

    @Override
    public List<Answer> loadAnswerByQuestionId(Long questionId) {
        return repository.findByQuestionId(questionId).stream()
                .map(AnswerJpaEntity::toDomain)
                .toList();
    }

    @Override
    public void deleteAnswerById(Long id) {
        repository.deleteById(id);
    }
}
