package com.zipte.platform.server.application.service;

import com.zipte.platform.core.response.ErrorCode;
import com.zipte.platform.core.util.AddressParser;
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
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import org.springframework.context.event.EventListener;

@Slf4j
@Service
@RequiredArgsConstructor
public class BatchService {

    /*
        GPT 기반 배치 처리 구현
     */

    private final RegionPort regionPort;
    private final RegionPricePort regionPricePort;
    private final LoadEstatePort loadEstatePort;
    private final EstatePricePort estatePricePort;

    @EventListener(ApplicationReadyEvent.class)
    public void runOnceAtStartup() {
        log.info("🔥 BatchService initialized - running saveRegionPrice()");
        saveRegionPrice();
    }

    // 매일 오전 3시 실행
    @Scheduled(cron = "0 20 3 * * *", zone = "Asia/Seoul")
    public void saveRegionPrice() {

        List<Region> regions = regionPort.loadAllRegions();

        for (Region region : regions) {
            try {
                Map<String, List<Double>> areaToPrices = new HashMap<>();

                // 평균가격 기본값 0으로 초기화
                Map<String, Double> averagePrices = new HashMap<>();
                averagePrices.put("15평 이하", 0.0);
                averagePrices.put("15~20평", 0.0);
                averagePrices.put("20~25평", 0.0);
                averagePrices.put("25~30평", 0.0);
                averagePrices.put("30평 이상", 0.0);

                String regionName = getRegionName(region.getCode());

                List<Estate> estates = loadEstatePort.loadEstatesByRegion(regionName);

                for (Estate estate : estates) {
                    String kaptCode = estate.getKaptCode();
                    List<EstatePrice> prices = estatePricePort.loadAllEstatePrices(kaptCode);

                    for (EstatePrice price : prices) {
                        double areaInPyeong = price.getExclusiveArea() / 3.3;

                        String areaCategory;
                        if (areaInPyeong < 15) {
                            areaCategory = "15평 이하";
                        } else if (areaInPyeong < 20) {
                            areaCategory = "15~20평";
                        } else if (areaInPyeong < 25) {
                            areaCategory = "20~25평";
                        } else if (areaInPyeong < 30) {
                            areaCategory = "25~30평";
                        } else {
                            areaCategory = "30평 이상";
                        }

                        double parsedPrice;
                        try {
                            parsedPrice = Double.parseDouble(price.getPrice().replace(",", ""));
                        } catch (NumberFormatException e) {
                            log.warn("[스프링 배치] 가격 변환 실패 - kaptCode: {}, price: {}", kaptCode, price.getPrice());
                            continue;
                        }

                        areaToPrices.computeIfAbsent(areaCategory, k -> new ArrayList<>()).add(parsedPrice);
                    }
                }

                for (Map.Entry<String, List<Double>> entry : areaToPrices.entrySet()) {
                    List<Double> priceList = entry.getValue();
                    if (!priceList.isEmpty()) {
                        double avg = priceList.stream()
                                .mapToDouble(Double::doubleValue)
                                .average()
                                .orElse(0.0);
                        averagePrices.put(entry.getKey(), avg);
                    }
                }

                RegionPrice regionPrice = RegionPrice.of(region.getCode(), averagePrices);
                regionPricePort.saveRegionPrice(regionPrice);

            } catch (Exception e) {
                log.error("[스프링 배치] 지역 처리 중 오류 발생 - regionCode: {}, error: {}", region.getCode(), e.getMessage(), e);
                // 예외 발생해도 계속 진행
            }
        }

        log.info("[스프링 배치] 모든 지역에 대한 평균 가격 저장 완료");
    }


    // regionCode → 지역명 파싱 함수
    private String getRegionName(String regionCode) {

        if (regionCode == null || regionCode.isBlank()) {
            throw new IllegalArgumentException("regionCode cannot be null or empty");
        }

        Region region = regionPort.loadRegion(regionCode)
                .orElseThrow(() -> new NoSuchElementException(ErrorCode.NOT_REGION.getMessage()));

        String address = region.getAddress();
        if (address == null || address.isBlank()) {
            throw new IllegalStateException("Address is null or empty for regionCode: " + regionCode);
        }

        var propertyAddress = AddressParser.parseAddress(address);
        if (propertyAddress == null) {
            throw new IllegalStateException("Failed to parse address: " + address);
        }

        String sidoCode = regionCode.substring(0, 2);
        String regionName = null;

        if (regionCode.endsWith("000000")) {
            if (isSpecialCity(sidoCode)) {
                regionName = propertyAddress.getDistrict();
            } else {
                regionName = propertyAddress.getCity();
            }
        } else if (regionCode.endsWith("00000")) {
            regionName = propertyAddress.getDistrict();
        } else if (regionCode.endsWith("00")) {
            regionName = propertyAddress.getDong();
        }

        if (regionName == null || regionName.isBlank()) {
            throw new IllegalStateException("Region name could not be determined for regionCode: " + regionCode);
        }

        return regionName;
    }

    private boolean isSpecialCity(String sidoCode) {
        return List.of("11", "26", "27", "28", "29", "30", "31", "36").contains(sidoCode);
    }
}
