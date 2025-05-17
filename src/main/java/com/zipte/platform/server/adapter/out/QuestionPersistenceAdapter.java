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

    /**
     * Persists a Question domain object and returns the saved instance.
     *
     * Converts the given Question to a JPA entity, saves it using the repository, and maps the persisted entity back to the domain model.
     *
     * @param question the Question to be saved
     * @return the saved Question with any updates from persistence (e.g., generated ID)
     */
    @Override
    public Question save(Question question) {

        var entity = QuestionJpaEntity.from(question);

        return repository.save(entity)
                .toDomain();
    }

    /**
     * Retrieves a question by its ID and maps it to the domain model.
     *
     * @param id the unique identifier of the question
     * @return an {@code Optional} containing the corresponding {@code Question} if found, or empty if not present
     */
    @Override
    public Optional<Question> loadQuestion(Long id) {
        return repository.findById(id)
                .map(QuestionJpaEntity::toDomain);
    }

    /**
     * Retrieves a paginated list of questions filtered by kaptCode.
     *
     * @param kaptCode the code used to filter questions
     * @param pageable pagination and sorting information
     * @return a page of questions matching the specified kaptCode
     */
    @Override
    public Page<Question> loadQuestionsByKaptCode(String kaptCode, Pageable pageable) {
        return repository.findByKaptCode(kaptCode, pageable)
                .map(QuestionJpaEntity::toDomain);
    }

    /**
     * Deletes a question from the repository by its unique identifier.
     *
     * @param id the unique identifier of the question to delete
     */
    @Override
    public void deleteQuestionById(Long id) {
        repository.deleteById(id);
    }

    /**
     * Determines whether a question with the specified ID exists in the database.
     *
     * @param questionId the ID of the question to check
     * @return true if a question with the given ID exists, false otherwise
     */
    @Override
    public boolean checkExistQuestion(Long questionId) {
        return repository.existsById(questionId);
    }

}
