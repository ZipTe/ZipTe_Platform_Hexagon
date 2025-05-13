package com.zipte.platform.server.adapter.in.web.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewRequest {

    // 멤버 관리
    @NotNull(message = "유저 아이디를 입력해주세요.")
    private Long userId;

    // 원하는 아파트 관리
    @NotNull(message = "아파트 코드를 입력해주세요.")
    private String kaptCode;

    // 리뷰 내용
    private String title;

    @Size(max = 100, message = "내용은 100자 이하여야 합니다.")
    private String content;

    // 리뷰 평점 (꼭 있어야 함)
    @NotNull(message = "교통 평점을 입력해주세요.")
    private Integer transport;

    @NotNull(message = "주변 환경 평점을 입력해주세요.")
    private Integer environment;

    @NotNull(message = "아파트 관리 평점을 입력해주세요.")
    private Integer apartmentManagement;

    @NotNull(message = "생활 편의성 평점을 입력해주세요.")
    private Integer livingEnvironment;

}
