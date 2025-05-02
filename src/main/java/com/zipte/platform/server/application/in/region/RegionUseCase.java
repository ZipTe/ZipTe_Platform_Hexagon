package com.zipte.platform.server.application.in.region;

import com.zipte.platform.server.domain.region.Region;

import java.util.List;

public interface RegionUseCase {

        /*
        카테고리 기반으로 지역 목록을 가져온다.
        예시 : [성남시> 분당구 > 야탑동]

        성남시 : 4113000000
        분당구 : 4113500000
        야탑동 : 4113510700

        법정동 코드 :
        41 : 시도
        135 : 시군구
        107 : 읍면동
        00 : 리
    */

    /// 특정 법정동 조회하기
    Region loadRegion(String regionCode);

    /// 특정 법정동의 하위 법정동 목록 조회하기
    List<Region> loadParentRegions(String regionCode);

}
