package com.zipte.platform.server.application.service;

import com.zipte.platform.core.response.ErrorCode;
import com.zipte.platform.server.application.in.estate.EstatePriceUseCase;
import com.zipte.platform.server.application.out.estate.EstatePricePort;
import com.zipte.platform.server.application.out.estate.LoadEstatePort;
import com.zipte.platform.server.domain.estate.EstatePrice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class EstatePriceService implements EstatePriceUseCase {

    private final LoadEstatePort loadEstatePort;
    private final EstatePricePort estatePricePort;

    @Override
    public List<EstatePrice> getEstatePriceByCode(String kaptCode) {

        /// 아파트 예외처리
        boolean checked = loadEstatePort.checkExistingByCode(kaptCode);

        if (!checked) {
            throw new NoSuchElementException(ErrorCode.NOT_ESTATE.getMessage());
        }

        /// 아파트 가격 조회
        return estatePricePort.loadAllEstatePrices(kaptCode);
    }

    @Override
    public List<EstatePrice> getEstatePriceByCode(String kaptCode, double exclusiveArea) {
        return estatePricePort.loadEstatePriceByCodeAndArea(kaptCode, exclusiveArea);
    }

    @Override
    public List<EstatePrice> getEstatePriceBetween(String kaptCode, Date startDate, Date endDate) {
        return List.of();
    }

    @Override
    public List<EstatePrice> getEstatePriceByYear(String kaptCode, int year) {
        return List.of();
    }
}
