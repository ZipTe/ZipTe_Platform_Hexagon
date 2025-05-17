package com.zipte.platform.server.application.out.community;

import com.zipte.platform.server.domain.community.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface QuestionPort {

    /// 저장
    Question save(Question question);

    /// 조회
    // 상세 조회하기
    Optional<Question> loadQuestion(Long id);

    // 아파트 이름별, 질문 목록 조회
    Page<Question> loadQuestionsByKaptCode(String kaptCode, Pageable pageable);

    /**
 * Deletes the question identified by the given ID.
 *
 * @param id the unique identifier of the question to delete
 */
    void deleteQuestionById(Long id);




    /****
 * Determines whether a question with the specified ID exists.
 *
 * @param questionId the unique identifier of the question to check
 * @return true if a question with the given ID exists; false otherwise
 */
    boolean checkExistQuestion(Long questionId);
}
