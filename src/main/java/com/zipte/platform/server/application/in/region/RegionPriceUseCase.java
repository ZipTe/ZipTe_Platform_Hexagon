package com.zipte.platform.server.application.in.region;

import com.zipte.platform.server.domain.region.RegionPrice;

public interface RegionPriceUseCase {

    /// 특정 법정동의 면적별 평균 거래 가격 조회하기
    RegionPrice loadRegionPriceByCode(String regionCode);

    /*
        기능 상 테스트를 위하여 넣어둔다.
     */

    /// 추후 어드민에서 사용할..
    void saveRegionPrice(RegionPrice regionPrice);

}
