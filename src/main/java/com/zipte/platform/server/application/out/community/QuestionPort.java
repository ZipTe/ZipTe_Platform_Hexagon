package com.zipte.platform.server.application.out.community;

import com.zipte.platform.server.domain.community.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;

public interface QuestionPort {

    /// 저장
    Question save(Question question);

    /// 조회
    // 상세 조회하기
    Optional<Question> loadQuestion(Long id);

    // 아파트별, 질문 목록 조회
    Page<Question> loadQuestionsByKaptCode(String kaptCode, Pageable pageable);

    // 아파트별, 키워드 기반 조회
    Page<Question> loadQuestionsByKeyword(String kaptCode, String keyword, Pageable pageable);

    List<Question> loadQuestionsByKeyword(String kaptCode, String keyword);

    /// 삭제
    void deleteQuestionById(Long id);




    /// 외부 의존성
    boolean checkExistQuestion(Long questionId);

    boolean checkExistQuestionByIdAndUserId(Long id, Long userId);

}
