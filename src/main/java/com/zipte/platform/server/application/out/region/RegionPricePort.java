package com.zipte.platform.server.application.out.region;

import com.zipte.platform.server.domain.region.RegionPrice;

import java.util.*;

public interface RegionPricePort {

    /// 저장
    RegionPrice saveRegionPrice(RegionPrice regionPrice);

    /// 수정
    void updateRegionPrice(RegionPrice newRegionPrice);

    /// 조회
    Optional<RegionPrice> loadRegionPriceByCode(String regionCode);

    boolean checkRegionPriceExist(String regionCode);

    List<RegionPrice> loadRegionPriceByCodes(List<String> regionCodes);

    /// 삭제
    void deleteRegionPriceByCode(String regionCode);

}
