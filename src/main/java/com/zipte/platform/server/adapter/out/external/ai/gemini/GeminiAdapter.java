package com.zipte.platform.server.adapter.out.external.ai.gemini;

import com.zipte.platform.server.adapter.out.external.ai.gemini.dto.ChatRequest;
import com.zipte.platform.server.adapter.out.external.ai.gemini.dto.ChatResponse;
import com.zipte.platform.server.application.out.external.ai.OpenAiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class GeminiAdapter implements OpenAiPort {


    @Qualifier("geminiRestTemplate")
    private final RestTemplate restTemplate;

    @Value("${gemini.api.url}")
    private String apiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Override
    public String summarizeText(String prompt) {

        // Gemini에 요청 전송
        String requestUrl = apiUrl + "?key=" + geminiApiKey;

        ChatRequest request = new ChatRequest(prompt);
        ChatResponse response = restTemplate.postForObject(requestUrl, request, ChatResponse.class);

        String message = response.getCandidates().get(0).getContent().getParts().get(0).getText().toString();

        return message;
    }

}
