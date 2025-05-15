package com.zipte.platform.server.application.in.external;

public interface OpenAiUseCase {

    /*
        AI에게 요청하여 아파트 정보를 요약하여 보여준다.
     */

    String getKaptCharacteristic(String kaptCode);

    
}
