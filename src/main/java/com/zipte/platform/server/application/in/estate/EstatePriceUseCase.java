package com.zipte.platform.server.application.in.estate;

import com.zipte.platform.server.domain.estate.EstatePrice;

import java.util.*;

public interface EstatePriceUseCase {

    /*
        아파트 실거래가 가져오는 유즈케이스
     */

    /// 최근 실거래가 가져오기
    List<EstatePrice> getEstatePriceByCode(String kaptCode);

    /// 최근 실거래가 가져오기
    List<EstatePrice> getEstatePriceByCode(String kaptCode, double exclusiveArea);

    /// 특정 기간 동안의 실거래가 가져오기
    List<EstatePrice> getEstatePriceBetween(String kaptCode, Date startDate, Date endDate);

    /// 특정 년도의 실거래가 가져오기
    List<EstatePrice> getEstatePriceByYear(String kaptCode, int year);

}
