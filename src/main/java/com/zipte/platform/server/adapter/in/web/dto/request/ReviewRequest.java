package com.zipte.platform.server.adapter.in.web.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewRequest {

    // 멤버 관리
    private Long userId;

    // 원하는 아파트 관리
    private String kaptCode;

    // 리뷰 내용
    private String title;
    private String content;

    // 리뷰 평점
    private int transport;
    private int environment;
    private int apartmentManagement;
    private int livingEnvironment;

    // 사진
}
