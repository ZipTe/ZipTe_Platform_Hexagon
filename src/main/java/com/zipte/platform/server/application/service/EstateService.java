package com.zipte.platform.server.application.service;

import com.zipte.platform.core.response.ErrorCode;
import com.zipte.platform.server.adapter.in.web.dto.response.EstateListResponse;
import com.zipte.platform.server.application.in.estate.GetEstateUseCase;
import com.zipte.platform.server.application.out.estate.EstatePricePort;
import com.zipte.platform.server.application.out.estate.LoadEstatePort;
import com.zipte.platform.server.application.out.property.LoadPropertyPort;
import com.zipte.platform.server.domain.estate.Estate;
import com.zipte.platform.server.domain.estate.EstatePrice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EstateService implements GetEstateUseCase {

    private final LoadEstatePort loadPort;

    private final LoadPropertyPort loadPropertyPort;

    /// 아파트 가격 조회
    private final EstatePricePort pricePort;


    @Override
    public List<EstateListResponse> loadEstatesNearBy(double latitude, double longitude, double radiusInKm) {
        double radiusInRadians = radiusInKm / 6378.1;  // 반경 km -> radians 변환

        /// 좌표 근처의 아파트 가져오기
        List<Estate> estates = loadPort.loadEstatesNearBy(latitude, longitude, radiusInRadians);

        List<EstateListResponse> responses = new ArrayList<>();

        /// 최신의 거래 목록 가져오기
        estates.forEach(estate -> {
            Optional<EstatePrice> price = pricePort.loadRecentPriceByKaptCode(estate.getKaptCode());
            EstateListResponse response = EstateListResponse.from(estate, price);
            responses.add(response);
        });

        return responses;

    }

    @Override
    public List<EstateListResponse> loadEstatesNearByProperty(double latitude, double longitude, double radiusInKm) {

        double radiusInRadians = radiusInKm / 6378.1;  // 반경 km -> radians 변환
        List<Estate> estates = loadPort.loadEstatesNearBy(latitude, longitude, radiusInRadians);

        List<EstateListResponse> result = new ArrayList<>();

        estates.forEach(estate -> {
            int count = loadPropertyPort.countPropertiesInEstate(estate.getKaptCode());
            if (count > 0) {
                result.add(EstateListResponse.from(estate, count));
            }
        });

        return result;
    }


    @Override
    public Estate loadEstateByCode(String kaptCode) {
        return loadPort.loadEstateByCode(kaptCode)
                .orElseThrow(() -> new NoSuchElementException(ErrorCode.NOT_ESTATE.getMessage()));
    }

    @Override
    public Estate loadEstateByName(String kaptName) {
        return loadPort.loadEstateByName(kaptName)
                .orElseThrow(() -> new NoSuchElementException(ErrorCode.NOT_ESTATE.getMessage()));
    }

    @Override
    public Page<Estate> loadEstatesByRegion(String region, Pageable pageable) {
        return loadPort.loadEstatesByRegion(region, pageable);
    }

    /// 아파트를 서로 비교하기
    @Override
    public List<Estate> loadEstatesByCompare(List<String> kaptCodes) {

        /// 예외처리를 통한 아파트 가져오기
        Estate first = loadPort.loadEstateByCode(kaptCodes.get(0))
                .orElseThrow(() -> new NoSuchElementException("first" + ErrorCode.NOT_ESTATE.getMessage()));

        Estate second = loadPort.loadEstateByCode(kaptCodes.get(1))
                .orElseThrow(() -> new NoSuchElementException("second" + ErrorCode.NOT_ESTATE.getMessage()));

        return List.of(first, second);

    }

}
