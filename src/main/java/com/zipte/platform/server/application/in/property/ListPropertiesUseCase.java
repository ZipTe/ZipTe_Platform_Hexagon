package com.zipte.platform.server.application.in.property;

import com.zipte.platform.server.adapter.in.web.dto.request.PropertyConditionRequest;
import com.zipte.platform.server.domain.property.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListPropertiesUseCase {
    /*
        매물정보 목록 가져오기 (최신순 정렬) ,
        지역별
        인기순 정렬
        조회순 정렬
     */

    Page<Property> getList(Pageable pageable);

    Page<Property> getListByCondition(PropertyConditionRequest request, Pageable pageable);

    Page<Property> getListByLikeCount(Pageable pageable);

    Page<Property> getListByViewCount(Pageable pageable);


}
