package com.zipte.platform.server.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionRequest {

    private Long userId;

    @NotNull
    private String kaptCode;

    @NotNull
    private String title;

    @NotNull
    private String content;

}
