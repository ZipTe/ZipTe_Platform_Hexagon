package com.zipte.platform.server.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerRequest {

    private Long userId;

    @NotNull(message = "질문 아이디는 필수입니다.")
    private Long questionId;

    @NotNull(message = "답변의 내용은 필수입니다.")
    private String content;

}
