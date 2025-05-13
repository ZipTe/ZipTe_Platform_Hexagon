package com.zipte.platform.server.adapter.in.web.dto.request;

import lombok.Data;

@Data
public class QuestionRequest {

    private Long userId;

    private String kaptCode;

    private String title;

    private String content;

}
