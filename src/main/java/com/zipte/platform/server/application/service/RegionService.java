package com.zipte.platform.server.application.service;

import com.zipte.platform.server.application.in.region.RegionUseCase;
import com.zipte.platform.server.application.out.region.RegionPort;
import com.zipte.platform.server.domain.region.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RegionService implements RegionUseCase {

    /*
        카테고리 기반으로 지역 목록을 가져온다.
        예시 : [성남시> 분당구 > 야탑동]
     */

    private final RegionPort regionPort;

    @Override
    public Region loadRegion(String regionCode) {
       return regionPort.loadRegion(regionCode);
    }

    /*
        법정동 코드 :
        41 : 시도
        135 : 시군구
        107 : 읍면동
        00 : 리

        41 130 00000 성남시
        41 131 00000 수정구
        41 135 00000 분당구

        ex : 성남시의 아래 코드이면
        01 234 567 89
        해당하는 01 234가 동일하지만,
            다른 567 89 값을 가져와야 한다.

        ex : 분당구의 아래 코드이면
        01 234 567 89
        해당하는 01 234 567가 동일하지만 다른 89
            값이 존재하는 코드를 가져와야 한다.


     */

    @Override
    public List<Region> loadChildRegionsByCode(String regionCode) {

        /// 예외처리 하기
        if (regionCode == null || regionCode.length() != 10) {
            throw new IllegalArgumentException("유효하지 않은 법정동 코드입니다.");
        }

        /// 뒤에 존재하는 0의 개수로 구분
        String prefix;

        /// 시도
        if (regionCode.endsWith("00000000")) {
            prefix = regionCode.substring(0, 2);
        }
        /// 시군구
        else if (regionCode.endsWith("00000")) {
            prefix = regionCode.substring(0, 4);
        }
        /// 읍면동
        else if (regionCode.endsWith("00")) {
            prefix = regionCode.substring(0, 8);
        }
        /// 리
        else {
            return List.of();
        }

        return regionPort.loadChildRegionsByPrefix(prefix);
    }
}
