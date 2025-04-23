package com.zipte.platform.server.application.out.property;

import com.zipte.platform.server.adapter.in.web.dto.request.PropertyConditionRequest;
import com.zipte.platform.server.domain.property.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface LoadPropertyPort {

    Optional<Property> loadProperty(Long id);

    // 기본 최신순
    Page<Property> loadList(Pageable pageable);

    // 인기순 가져오기
    Page<Property> loadListByLikeCount(Pageable pageable);

    // 조회순 가져오기
    Page<Property> loadListByViewCount(Pageable pageable);

    // 조건에 따른 정보 가져오기
    Page<Property> loadListByCondition(Pageable pageable, PropertyConditionRequest request);

    // 특정 유저가 내놓은 아이템 리스트 중에 포함하는지 확인
    boolean existsPropertyBySellerId(Long sellerId);

    // 특정 유저가 내놓은 아이템과 매물 조회
    Optional<Property> loadPropertyBySellerIdAndPropertyId(Long sellerId, Long propertyId);

    // 특정 아파트에 매물이 몇 개 있는지 조회
    int countPropertiesInEstate(String kaptCode);


}
