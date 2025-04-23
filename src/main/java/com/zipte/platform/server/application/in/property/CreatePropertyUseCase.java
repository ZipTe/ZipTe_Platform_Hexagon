package com.zipte.platform.server.application.in.property;

import com.zipte.platform.server.adapter.in.web.dto.request.PropertyRequest;
import com.zipte.platform.server.domain.property.Property;

public interface CreatePropertyUseCase {

    /*
        교환할 매물정보 등록하기
     */

    Property create(PropertyRequest request); // 매물 생성



}
