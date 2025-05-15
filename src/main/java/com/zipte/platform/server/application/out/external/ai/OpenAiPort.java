package com.zipte.platform.server.application.out.external.ai;

public interface OpenAiPort {

    /*
        특징 요약 AI 사용을 위한 포트를 정의한다.
     */

    /// 특징 요청하기
    String summarizeText(String prompt);



}
