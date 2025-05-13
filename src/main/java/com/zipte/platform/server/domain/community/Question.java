package com.zipte.platform.server.domain.community;

import com.zipte.platform.server.domain.BaseDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Question extends BaseDomain {

    private Long id;

    private Long userId;

    private String kaptCode;

    private String title;

    private String content;

    private QuestionStatistics statistics;

}
