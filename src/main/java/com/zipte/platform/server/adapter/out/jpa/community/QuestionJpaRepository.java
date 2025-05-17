package com.zipte.platform.server.adapter.out.jpa.community;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionJpaRepository extends JpaRepository<QuestionJpaEntity, Long> {

    /**
 * Retrieves a paginated list of questions filtered by the specified kaptCode.
 *
 * @param kaptCode the code used to filter questions
 * @param pageable pagination and sorting information
 * @return a page of QuestionJpaEntity objects matching the kaptCode
 */
Page<QuestionJpaEntity> findByKaptCode(String kaptCode, Pageable pageable);

    /**
 * Checks whether a question entity with the specified ID exists in the repository.
 *
 * @param id the unique identifier of the question entity
 * @return true if an entity with the given ID exists, false otherwise
 */
boolean existsById(Long id);
}
