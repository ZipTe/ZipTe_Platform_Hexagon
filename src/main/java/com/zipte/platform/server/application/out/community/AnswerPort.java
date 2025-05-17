package com.zipte.platform.server.application.out.community;

import com.zipte.platform.server.domain.community.Answer;

import java.util.*;
import java.util.Optional;

public interface AnswerPort {

    /// 저장
    Answer saveAnswer(Answer answer);

    /// 조회
    /****
 * Retrieves an answer by its unique identifier.
 *
 * @param id the unique identifier of the answer
 * @return an {@code Optional} containing the found answer, or empty if not found
 */
    Optional<Answer> loadAnswerById(Long id);

    /**
 * Retrieves the primary or first answer associated with the specified question ID.
 *
 * @param questionId the unique identifier of the question
 * @return an {@code Optional} containing the first or representative {@code Answer}, or empty if none exists
 */
    Optional<Answer> loadAnswerByQuestionIdFirst(Long questionId);

    /**
 * Retrieves all answers associated with the specified question ID.
 *
 * @param questionId the unique identifier of the question
 * @return a list of answers linked to the given question ID; the list may be empty if no answers exist
 */
    List<Answer> loadAnswerByQuestionId(Long questionId);

    /****
 * Deletes the answer identified by the given ID.
 *
 * @param id the unique identifier of the answer to delete
 */
    void deleteAnswerById(Long id);

}
