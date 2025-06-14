package com.zipte.platform.server.adapter.out.external.ai.zipteAi;

import com.zipte.platform.core.response.ErrorCode;
import com.zipte.platform.server.adapter.out.external.ai.zipteAi.dto.ZipteRequest;
import com.zipte.platform.server.adapter.out.external.ai.zipteAi.dto.ZipteResponse;
import com.zipte.platform.server.application.out.external.ai.RecommendAIPort;
import com.zipte.platform.server.application.out.user.UserWeightPort;
import com.zipte.platform.server.domain.user.UserWeight;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class ZipteAdapter implements RecommendAIPort {

    /// WebClient 기반으로 진행
    private final WebClient webClient;

    /// 외부 의존성
    private final UserWeightPort weightPort;

    @Override
    public List<ZipteResponse> recommend(Long userId) {

        /// 유저의 가중치 만들기
        UserWeight weight = weightPort.loadUserWeightByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException(ErrorCode.NOT_WEIGHT.getMessage()));

        /// 가중치를 바탕으로 Request 전송하기
        ZipteRequest request = ZipteRequest.from(weight);

        /// 응답 값 받기
        List<ZipteResponse> list = webClient.post()
                .uri("/recommend")
                .bodyValue(request)
                .retrieve()
                .bodyToFlux(ZipteResponse.class)
                .collectList()
                .block();// 명시적이고 안전함

        return list;
    }

}
