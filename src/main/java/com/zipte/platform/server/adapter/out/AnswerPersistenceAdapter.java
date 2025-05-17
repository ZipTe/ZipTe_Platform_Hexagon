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

    /**
     * Persists an Answer domain object and returns the saved instance.
     *
     * Converts the provided Answer to a JPA entity, saves it using the repository, and maps the result back to the domain model.
     *
     * @param answer the Answer domain object to be saved
     * @return the saved Answer domain object
     */
    @Override
    public Answer saveAnswer(Answer answer) {
        var entity = AnswerJpaEntity.from(answer);

        return repository.save(entity)
                .toDomain();
    }

    /**
     * Retrieves an answer by its ID and maps it to the domain model.
     *
     * @param id the unique identifier of the answer
     * @return an {@code Optional} containing the answer if found, or empty if not present
     */
    @Override
    public Optional<Answer> loadAnswerById(Long id) {
        return repository.findById(id)
                .map(AnswerJpaEntity::toDomain);
    }

    /**
     * Retrieves the earliest created answer associated with the specified question ID.
     *
     * @param questionId the ID of the question whose earliest answer is to be retrieved
     * @return an {@code Optional} containing the earliest {@code Answer} if found, or empty if none exist
     */
    @Override
    public Optional<Answer> loadAnswerByQuestionIdFirst(Long questionId) {
        return repository.findTop1ByQuestionIdOrderByCreatedAtAsc(questionId)
                .map(AnswerJpaEntity::toDomain);
    }

    /**
     * Retrieves all answers associated with the specified question ID.
     *
     * @param questionId the ID of the question whose answers are to be retrieved
     * @return a list of answers linked to the given question ID
     */
    @Override
    public List<Answer> loadAnswerByQuestionId(Long questionId) {
        return repository.findByQuestionId(questionId).stream()
                .map(AnswerJpaEntity::toDomain)
                .toList();
    }

    /**
     * Deletes an answer from the repository by its unique ID.
     *
     * @param id the unique identifier of the answer to delete
     */
    @Override
    public void deleteAnswerById(Long id) {
        repository.deleteById(id);
    }
}
