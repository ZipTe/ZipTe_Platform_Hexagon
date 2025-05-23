package com.zipte.platform.server.application.out.estate;

import com.zipte.platform.server.domain.estate.EstatePrice;

import java.util.List;
import java.util.Optional;

public interface EstatePricePort {

    /// 가장 최근의 거래 조회하기
    Optional<EstatePrice> loadRecentPriceByKaptCode(String kaptCode);

    /// 목록 조회하기
    List<EstatePrice> loadAllEstatePrices(String kaptCode);

    /// 코드 및 평수 기반 조회하기
    List<EstatePrice> loadEstatePriceByCodeAndArea(String kaptCode, double exclusiveArea);

    /// 기간별 조회하기
    List<EstatePrice> loadAllEstatePricesBetween(String kaptCode, String from, String to);


}
