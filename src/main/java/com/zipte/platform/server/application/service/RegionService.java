package com.zipte.platform.server.application.service;

import com.zipte.platform.core.response.ErrorCode;
import com.zipte.platform.server.application.in.region.RegionUseCase;
import com.zipte.platform.server.application.out.region.RegionPort;
import com.zipte.platform.server.domain.region.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

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

        /// 양식 예외처리 하기
        if (regionCode == null || regionCode.length() != 10) {
            throw new IllegalArgumentException("유효하지 않은 법정동 코드입니다.");
        }

        /// 예외처리
        return regionPort.loadRegion(regionCode)
                .orElseThrow(() -> new NoSuchElementException(ErrorCode.NOT_REGION.getMessage()));
    }

    /*
        법정동 코드 :
        41 : 시도
        135 : 시군구
        107 : 읍면동
        00 : 리

        41 000 000 00 경기도
        41 130 000 00 성남시
        41 131 000 00 수정구
        41 135 000 00 분당구
        41 135 107 00 야탑동

        11 000 000 00 서울특별시
        11 110 000 00 종로구
        11 110 101 00 종로구 청운동

     */

    @Override
    public List<Region> loadChildRegionsByCode(String regionCode) {

        /// 예외처리 하기
        if (regionCode == null || regionCode.length() != 10) {
            throw new IllegalArgumentException("유효하지 않은 법정동 코드입니다.");
        }

        String sidoCode = regionCode.substring(0, 2);  // 시도 코드 추출

        /// 뒤에 존재하는 0의 개수로 구분
        String prefix;
        String suffix;

        /// 시도
        if (regionCode.endsWith("00000000")) {
            prefix = sidoCode;
            suffix = "000000";
        }

        /// 시군구
        // 성남시
        else if (regionCode.endsWith("000000")) {

            // 시도에 따라 분기
            if (isSpecialCity(sidoCode)) {
                prefix = regionCode.substring(0, 5); // 자치구 (11290)
                suffix = "00";

            } else {
                prefix = regionCode.substring(0, 4); // 일반 시군구 (4113)
                suffix = "00000";
            }

        }
        // 자치구
        else if (regionCode.endsWith("00000")) {
            prefix = regionCode.substring(0, 5);
            suffix = "00";
        }

        /// 읍면동
        else if (regionCode.endsWith("00")) {
            prefix = regionCode.substring(0, 8);
            suffix = "";
        }

        /// 리
        else {
            return List.of();
        }

        return regionPort.loadChildRegionsByPrefix(prefix, suffix);
    }

    private boolean isSpecialCity(String sidoCode) {
        // 특별시 및 광역시 목록
        return List.of("11", "26", "27", "28", "29", "30", "31", "36").contains(sidoCode);
    }
}
