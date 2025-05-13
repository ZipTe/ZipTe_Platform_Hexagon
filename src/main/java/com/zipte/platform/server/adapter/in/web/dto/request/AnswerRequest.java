package com.zipte.platform.server.adapter.in.web.dto.request;

import lombok.Data;

@Data
public class AnswerRequest {

    private Long userId;

    private Long questionId;

    private String content;

}
