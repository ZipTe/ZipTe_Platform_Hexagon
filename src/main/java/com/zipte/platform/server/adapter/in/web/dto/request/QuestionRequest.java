package com.zipte.platform.server.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionRequest {

    private Long userId;

    @NotNull(message = "아파트 코드는 필수 조건입니다.")
    private String kaptCode;

    @NotNull(message = "질문의 제목은 필수입니다.")
    private String title;

    @NotNull(message = "질문의 내용이 존재해야합니다.")
    @Size(max = 1000)
    private String content;


}
