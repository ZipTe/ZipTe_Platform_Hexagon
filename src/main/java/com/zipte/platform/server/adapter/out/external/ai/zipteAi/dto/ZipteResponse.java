package com.zipte.platform.server.adapter.out.external.ai.zipteAi.dto;

public record ZipteResponse(
        String kaptCode,
        String kaptName,
        double recommendationScore,
        String reason
) {
}
