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

    private String title;

    private String content;

    private String imageUrl;

    private int facilities;

    private int infrastructure;

    private int neighborhood;

    private boolean certified;

    /// 정적 팩토리 메서드
    public static Review of(Long userId, String kaptCode, String title, String content, String imageUrl, int facilities, int infrastructure, int neighborhood, boolean certified) {

        return Review.builder()
                .id(userId)
                .kaptCode(kaptCode)
                .title(title)
                .content(content)
                .imageUrl(imageUrl)
                .facilities(facilities)
                .infrastructure(infrastructure)
                .neighborhood(neighborhood)
                .certified(certified)
                .build();
    }



}
