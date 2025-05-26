package com.zipte.platform.server.application.out.region;

import com.zipte.platform.server.domain.region.Region;

import java.util.List;
import java.util.Optional;

public interface RegionPort {

    /// 법정동 상세 조회
    Optional<Region> loadRegion(String regionCode);

    /// 법정동의 하위 법정동 목록 조회
    List<Region> loadChildRegionsByPrefix(String prefix, String suffix);

    /// 존재 여부를 체크하는 boolean
    boolean checkExistCode(String regionCode);

    /// 코드를 바탕으로 한번에 조회하기
    List<Region> loadRegionsByCodes(List<String> codes);


    /// 배치 처리
    List<Region> loadAllRegions();
}
