package com.zipte.platform.server.domain.notification;

import com.zipte.platform.server.domain.property.Property;
import com.zipte.platform.server.domain.property.PropertyAddress;
import com.zipte.platform.server.domain.property.PropertySnippet;
import com.zipte.platform.server.domain.property.PropertyType;

public class PropertyFixtures {

    public static Property stubs(Long ownerId, String kaptCode) {
        PropertyAddress address = PropertyAddress.of(
                "경기 성남시 분당구 야탑로 123", // 도로명 주소
                "101동 202호",                // 상세 주소
                "13579"                      // 우편번호
        );

        PropertySnippet snippet = PropertySnippet.of(
                "장미 동부 아파트",
                "채광 좋고 조용한 동입니다.",
                3,  // 방 개수
                2,  // 욕실 개수
                2005 // 건축 연도
        );

        return Property.of(
                ownerId,
                PropertyType.APARTMENT,
                address,
                snippet,
                250000000L, // 가격
                kaptCode
        );
    }

    public static Property stub(Long userId, String kaptCode) {
        return stubs(userId, kaptCode);
    }
}

