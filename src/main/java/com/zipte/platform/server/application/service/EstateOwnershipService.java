package com.zipte.platform.server.application.service;

import com.zipte.platform.core.response.ErrorCode;
import com.zipte.platform.server.adapter.in.web.dto.request.EstateOwnershipRequest;
import com.zipte.platform.server.application.in.estateOwnership.EstateOwnershipUseCase;
import com.zipte.platform.server.application.out.estate.LoadEstatePort;
import com.zipte.platform.server.application.out.estateOwnership.EstateOwnerShipPort;
import com.zipte.platform.server.application.out.user.UserPort;
import com.zipte.platform.server.application.service.exception.AlreadyExistingEstateException;
import com.zipte.platform.server.application.service.exception.NotExistingEstateInYourAreaException;
import com.zipte.platform.server.domain.estate.Estate;
import com.zipte.platform.server.domain.estateOwnership.EstateOwnership;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EstateOwnershipService implements EstateOwnershipUseCase {

    private final EstateOwnerShipPort port;

    /// 의존성
    private final UserPort loadUserPort;
    private final LoadEstatePort loadEstatePort;

    private final double EARTH_RADIUS_KM = 6373;
    private final double ONE_KM_IN_RADIANS = 1 / EARTH_RADIUS_KM;


    @Override
    public EstateOwnership createOwnership(EstateOwnershipRequest request) {

        /// 유저 예외처리
        boolean userChecked = loadUserPort.checkExistingById(request.getUserId());
        if (!userChecked) {
            throw new NoSuchElementException(ErrorCode.NOT_USER.getMessage());
        }

        /// 아파트 예외처리
        boolean checked = loadEstatePort.checkExistingByCode(request.getKaptCode());
        if (!checked) {
            throw new NoSuchElementException(ErrorCode.NOT_ESTATE.getMessage());
        }

        /// 이미 등록했는지 여부 체크
        boolean existCheck = port.loadOwnershipByUser(request.getUserId(), request.getKaptCode());
        if (existCheck) {
            throw new AlreadyExistingEstateException(ErrorCode.BAD_REQUEST_ESTATE.getMessage());
        }

        /// 구매 날짜 예외처리
        if (request.getBoughtAt().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException(ErrorCode.NOT_DATE.getMessage());
        }

        /// 현 위치와 아파트 위치와의 비교 예외처리
        // 현 위치 반경 1KM 조회에 아파트 존재하는지 체크
        List<Estate> list = loadEstatePort.loadEstatesNearBy(request.getLongitude(), request.getLatitude(), ONE_KM_IN_RADIANS);

        boolean hasKaptCode = list.stream()
                .anyMatch(f -> f.getKaptCode().equals(request.getKaptCode()));

        if (!hasKaptCode) {
            throw new NotExistingEstateInYourAreaException(ErrorCode.NOT_ESTATE_IN_YOUR_AREA.getMessage());
        }

        /// 객체 생성
        var ownership = EstateOwnership.of(request.getUserId(), request.getKaptCode(), request.getBoughtAt());

        return port.saveOwnership(ownership);
    }

    @Override
    public void deleteOwnership(Long userId, String kaptCode) {
        port.deleteOwnership(userId, kaptCode);
    }


}
