package com.zipte.platform.server.application.out.community;

import com.zipte.platform.server.domain.community.Answer;

import java.util.Optional;

public interface AnswerPort {

    /// 저장
    Answer saveAnswer(Answer answer);

    /// 조회
    // 답변 조회하기
    Optional<Answer> loadAnswerById(String id);

    /// 삭제
    void deleteAnswerById(Long id);

}
