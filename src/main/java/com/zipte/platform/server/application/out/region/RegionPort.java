package com.zipte.platform.server.application.out.region;

import com.zipte.platform.server.domain.region.Region;

import java.util.List;

public interface RegionPort {

    /// 법정동 상세 조회
    Region loadRegion(String regionCode);

    /// 법정동의 하위 법정동 목록 조회
    List<Region> loadChildRegionsByPrefix(String prefix, String suffix);

    /// 존재 여부를 체크하는 boolean
    boolean checkExistCode(String regionCode);

}
