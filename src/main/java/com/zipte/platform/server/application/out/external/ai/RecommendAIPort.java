package com.zipte.platform.server.application.out.external.ai;

import com.zipte.platform.server.adapter.out.external.ai.zipteAi.dto.ZipteResponse;

import java.util.List;

public interface RecommendAIPort {

    /*
        추천 AI 사용을 위한 포트를 정의한다.
     */

    /// 유저별로 가중치를 가져와서 전송하고 결과를 받는다.
    List<ZipteResponse> recommend(Long userId);


}
