package com.zipte.platform.server.adapter.out.jpa.community;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerJpaRepository extends JpaRepository<AnswerJpaEntity, Long> {

    /****
 * Retrieves all answer entities associated with the specified question ID.
 *
 * @param questionId the ID of the question whose answers are to be retrieved
 * @return a list of answer entities linked to the given question ID
 */
List<AnswerJpaEntity> findByQuestionId(Long questionId);

    /****
 * Retrieves the earliest created answer entity for the specified question.
 *
 * @param questionId the ID of the question to search answers for
 * @return an {@code Optional} containing the earliest created {@code AnswerJpaEntity} for the given question, or empty if none exist
 */
Optional<AnswerJpaEntity> findTop1ByQuestionIdOrderByCreatedAtAsc(Long questionId);


}
