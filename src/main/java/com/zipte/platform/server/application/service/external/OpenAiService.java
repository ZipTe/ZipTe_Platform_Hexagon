package com.zipte.platform.server.application.service.external;

import com.zipte.platform.core.response.ErrorCode;
import com.zipte.platform.server.application.in.external.OpenAiUseCase;
import com.zipte.platform.server.application.out.estate.LoadEstatePort;
import com.zipte.platform.server.application.out.external.ai.OpenAiPort;
import com.zipte.platform.server.domain.estate.Estate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OpenAiService implements OpenAiUseCase {

    private final OpenAiPort openAiPort;
    private final LoadEstatePort loadEstatePort;

    @Override
    public String getKaptCharacteristic(String kaptCode) {

        /// 아파트 예외처리
        Estate estate = loadEstatePort.loadEstateByCode(kaptCode)
                .orElseThrow(() -> new NoSuchElementException(ErrorCode.NOT_ESTATE.getMessage()));

        String prompt = estate.getKaptName() + "를 이해하기 쉽게 특징을 바탕으로 요약해줘";

        /// 요청하기
        return openAiPort.summarizeText(prompt);
    }
}
