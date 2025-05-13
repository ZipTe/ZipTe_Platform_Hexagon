package com.zipte.platform.server.application.in.community;

import com.zipte.platform.server.adapter.in.web.dto.request.AnswerRequest;
import com.zipte.platform.server.domain.community.Answer;

public interface AnswerUseCase {

    /*
        - 실거주 인증 유저는 답변을 할 수 있다.
        - 비실거주 인증 유저는 답변할 질문의 목록이 뜨지 않는다.
     */

    /// 답변 하기
    Answer createComment(AnswerRequest request);

}
