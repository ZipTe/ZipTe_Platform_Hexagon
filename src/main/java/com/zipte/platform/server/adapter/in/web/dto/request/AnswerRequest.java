package com.zipte.platform.server.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerRequest {

    private Long userId;

    @NotNull
    private Long questionId;

    @NotNull
    private String content;

}
