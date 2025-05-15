package com.zipte.platform.server.application.service;

import com.zipte.platform.core.response.ErrorCode;
import com.zipte.platform.core.util.AddressParser;
import com.zipte.platform.server.application.in.region.RegionPriceUseCase;
import com.zipte.platform.server.application.out.estate.EstatePricePort;
import com.zipte.platform.server.application.out.estate.LoadEstatePort;
import com.zipte.platform.server.application.out.region.RegionPort;
import com.zipte.platform.server.application.out.region.RegionPricePort;
import com.zipte.platform.server.domain.estate.Estate;
import com.zipte.platform.server.domain.estate.EstatePrice;
import com.zipte.platform.server.domain.region.Region;
import com.zipte.platform.server.domain.region.RegionPrice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RegionPriceService implements RegionPriceUseCase {

    private final RegionPricePort regionPricePort;

    /// 외부 의존성
    private final RegionPort regionPort;
    private final LoadEstatePort loadEstatePort;
    private final EstatePricePort estatePricePort;

    @Override
    public RegionPrice loadRegionPriceByCode(String regionCode) {

        return regionPricePort.loadRegionPriceByCode(regionCode)
                .orElseThrow(() -> new NoSuchElementException(ErrorCode.NOT_REGION_PRICE.getMessage()));
    }

    /// 스프링 배치를 통해, 야간마다 진행 예정
    /// 일단은 코드의 완성을 위해서, 일단 임의로 구현할 예정
    /// GPT의 도움이 컸다 .. 고맙습니다 ..
    @Override
    public void saveRegionPrice(String regionCode) {

        ///
        log.info("Save region price [{}]", regionCode);

        /// 조회할 지역 코드 조회
        String regionName = getRegionName(regionCode);

        /// 해당 지역의 아파트 목록 조회
        List<Estate> estates = loadEstatePort.loadEstatesByRegion(regionName);

        /// 아파트별 가격 조회
        Map<String, List<Double>> areaToPrices = new HashMap<>();

        estates.forEach(estate -> {
            String kaptCode = estate.getKaptCode();

            List<EstatePrice> prices = estatePricePort.loadAllEstatePrices(kaptCode);

            prices.forEach(price -> {
                double areaInSquareMeter = price.getExclusiveArea();
                double areaInPyeong = areaInSquareMeter / 3.3;  // 평으로 변환

                /// 면적대 그룹화
                String areaCategory;
                if (areaInPyeong < 15) {
                    areaCategory = "15평 이하";
                } else if (areaInPyeong >= 15 && areaInPyeong < 20) {
                    areaCategory = "15~20평";
                } else if (areaInPyeong >= 20 && areaInPyeong < 25) {
                    areaCategory = "20~25평";
                } else if (areaInPyeong >= 30) {
                    areaCategory = "30평 이상";
                } else {
                    return;  // 제외할 수 있는 구간
                }

                /// 가격 정보 추가
                areaToPrices.computeIfAbsent(areaCategory, k ->
                        new ArrayList<>()).add(Double.parseDouble(price.getPrice().replace(",", "")));
            });
        });

        /// 지역의 평균 가격 계산
        Map<String, Double> averagePrices = new HashMap<>();

        ///
        areaToPrices.forEach((area, prices) -> {
            double avg = prices.stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0.0);

            averagePrices.put(area, avg);
        });

        RegionPrice regionPrice = RegionPrice.of(regionCode, averagePrices);

        /// upsert
        if (regionPricePort.checkRegionPriceExist(regionCode)) {
            // 기존에 있었다면 업데이트
            regionPricePort.updateRegionPrice(regionPrice);
        } else {
            // 기존에 없었다면 새로 추가
            regionPricePort.saveRegionPrice(regionPrice);
        }

    }

    /// 내부 함수
    private String getRegionName(String regionCode) {

        /// 가져오기
        Region region = regionPort.loadRegion(regionCode)
                .orElseThrow(() -> new NoSuchElementException(ErrorCode.NOT_REGION.getMessage()));

        String address = region.getAddress();

        /// 파싱 하기
        var propertyAddress = AddressParser.parseAddress(address);

        String sidoCode = regionCode.substring(0, 2);  // 시도 코드 추출
        String regionName = "";

        /// 시군구
        // 성남시
        if (regionCode.endsWith("000000")) {

            // 시도에 따라 분기
            if (isSpecialCity(sidoCode)) {
                regionName = propertyAddress.getDistrict();

            } else {
                regionName = propertyAddress.getCity();
            }
        }

        // 자치구
        else if (regionCode.endsWith("00000")) {
            regionName = propertyAddress.getDistrict();
        }

        /// 읍면동
        else if (regionCode.endsWith("00")) {
            regionName = propertyAddress.getDong();
        }
        return regionName;
    }

    /// 내부 함수
    private boolean isSpecialCity(String sidoCode) {
        // 특별시 및 광역시 목록
        return List.of("11", "26", "27", "28", "29", "30", "31", "36").contains(sidoCode);
    }

}
