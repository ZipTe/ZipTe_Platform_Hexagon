package com.zipte.platform.server.application.in.community;

import com.zipte.platform.server.adapter.in.web.dto.request.QuestionAnswerDetailResponse;
import com.zipte.platform.server.adapter.in.web.dto.request.QuestionRequest;
import com.zipte.platform.server.adapter.in.web.dto.response.QuestionAnswerListResponse;
import com.zipte.platform.server.domain.community.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionUseCase {

    /*
        - 로그인한 유저는 단지별로 질문을 남기고, 답변을 받을 수 있다
     */


    /// 아파트에 대한 질문 하기
    Question createQuestion(QuestionRequest request);

    /// 답변과 함께 조회 하기
    QuestionAnswerDetailResponse loadQuestion(Long questionId);

    /// 질문 목록 조회하기
    Page<QuestionAnswerListResponse> loadQuestions(String kaptCode, Pageable pageable);

    /// 질문 삭제하기
    void deleteQuestion(Long questionId, Long userId);

}
