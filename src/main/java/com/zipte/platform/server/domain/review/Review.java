package com.zipte.platform.server.domain.review;

import com.zipte.platform.server.domain.BaseDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Review extends BaseDomain {

    private Long id;

    private Long userId;

    private String kaptCode;

    private String imageUrl;

    private ReviewSnippet snippet;

    private ReviewStatistics statistics;

    private boolean certified;

    /// 정적 팩토리 메서드
    /// 아직 이미지는 포함 X
    public static Review of(Long userId, String kaptCode, ReviewSnippet snippet, boolean certified) {

        return Review.builder()
                .id(userId)
                .kaptCode(kaptCode)
                .imageUrl("imageUrl")
                .snippet(snippet)
                .statistics(ReviewStatistics.of())
                .certified(certified)
                .build();
    }



}
