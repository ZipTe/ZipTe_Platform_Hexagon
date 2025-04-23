package com.zipte.platform.server.domain.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ReviewSnippet {

    private String title;
    private String content;

    // 교통 여건 평점
    private int transport;

    // 주변 환경 평점
    private int environment;

    // 단지 관리 평점
    private int apartmentManagement;

    // 거주 환경 평점
    private int livingEnvironment;

    // 전체 평점
    private int overall;

    // 생성 로직
    public static ReviewSnippet of(String title, String content,int transport, int environment, int apartmentManagement, int livingEnvironment) {
        return ReviewSnippet.builder()
                .title(title)
                .content(content)
                .transport(transport)
                .environment(environment)
                .apartmentManagement(apartmentManagement)
                .livingEnvironment(livingEnvironment)
                .build();
    }


}
