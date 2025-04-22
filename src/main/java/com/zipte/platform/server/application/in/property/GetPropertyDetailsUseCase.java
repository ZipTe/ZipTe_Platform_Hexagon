package com.zipte.platform.server.application.in.property;

import com.zipte.platform.server.domain.property.Property;

public interface GetPropertyDetailsUseCase {

    /*
        매물 상세정보 가져오기
     */

    Property getPropertyById(Long propertyId);


}
