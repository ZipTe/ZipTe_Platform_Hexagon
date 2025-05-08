package com.zipte.platform.server.application.out.region;

import com.zipte.platform.server.domain.region.RegionPrice;

public interface RegionPricePort {

    /// 저장
    RegionPrice saveRegionPrice(RegionPrice regionPrice);


    /// 조회
    RegionPrice getRegionPriceByCode(String regionCode);

    /// 삭제
    void deleteRegionPriceByCode(String regionCode);

}
