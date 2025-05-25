package com.zipte.platform.server.application.in.user;

import com.zipte.platform.server.adapter.in.web.dto.request.UserWeightRequest;
import com.zipte.platform.server.domain.user.UserWeight;

public interface UserWeightUseCase {

    /*
    - 설정
       사용자가 중요하게 생각하는 요소에 따라 가중치 부여
        [ • 편의시설(마트, 병원 등 근접성)
        • 교통(지하철, 버스 정류장까지 소요 시간)
        • 지역 선호도
        • 공원 접근성
        • 나의 직장/학교 위치 지정
        • 직장/학교와의 거리 ]
    - 해제
        모든 가중치 값을 0으로 초기화한다.

     */

    UserWeight createWeight(UserWeightRequest request);

    UserWeight getWeight(Long userId);

    void removeWeight(Long userId, Long weightId);



}
