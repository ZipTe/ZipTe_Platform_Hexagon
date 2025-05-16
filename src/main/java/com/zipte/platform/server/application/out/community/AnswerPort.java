package com.zipte.platform.server.application.out.community;

import com.zipte.platform.server.domain.community.Answer;

import java.util.*;
import java.util.Optional;

public interface AnswerPort {

    /// 저장
    Answer saveAnswer(Answer answer);

    /// 조회
    // 답변 조회하기
    Optional<Answer> loadAnswerById(Long id);

    // 대표 답변 조회
    Optional<Answer> loadAnswerByQuestionIdFirst(Long questionId);

    // 질문에 따른 답변 목록 가져오기
    List<Answer> loadAnswerByQuestionId(Long questionId);

    /// 삭제
    void deleteAnswerById(Long id);

}
