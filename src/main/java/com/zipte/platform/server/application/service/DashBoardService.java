package com.zipte.platform.server.application.service;

import com.zipte.platform.core.response.ErrorCode;
import com.zipte.platform.server.application.in.dashboard.DashBoardUseCase;
import com.zipte.platform.server.application.out.estate.EstatePricePort;
import com.zipte.platform.server.application.out.estate.LoadEstatePort;
import com.zipte.platform.server.application.out.estateOwnership.EstateOwnerShipPort;
import com.zipte.platform.server.application.out.favorite.FavoritePort;
import com.zipte.platform.server.application.out.region.RegionPort;
import com.zipte.platform.server.application.out.region.RegionPricePort;
import com.zipte.platform.server.application.out.user.UserPort;
import com.zipte.platform.server.domain.estate.Estate;
import com.zipte.platform.server.domain.estate.EstatePrice;
import com.zipte.platform.server.domain.estateOwnership.EstateOwnership;
import com.zipte.platform.server.domain.favorite.Favorite;
import com.zipte.platform.server.domain.favorite.FavoriteType;
import com.zipte.platform.server.domain.region.Region;
import com.zipte.platform.server.domain.region.RegionPrice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DashBoardService implements DashBoardUseCase {


    private final UserPort userPort;

    private final FavoritePort favoritePort;

    private final RegionPort regionPort;

    private final RegionPricePort regionPricePort;

    private final LoadEstatePort estatePort;

    private final EstatePricePort estatePricePort;

    private final EstateOwnerShipPort estateOwnerShipPort;

    /// 나의 관심 지역 조회
    @Override
    public List<Region> getFavoriteRegion(Long userId) {

        /// 유저 예외 처리
        checkUser(userId);

        /// 유저의 관심 지역 목록 조회하기
        List<Favorite> regionList = favoritePort.loadUserFavoriteByType(userId, FavoriteType.REGION);

        /// 관심 지역 목록을 바탕으로 조회하기
        List<String> regionCodes = regionList.stream()
                .map(Favorite::getRegionCode)
                .toList();

        return regionPort.loadRegionsByCodes(regionCodes);
    }

    @Override
    public List<RegionPrice> getFavoriteRegionPrices(Long userId) {

        /// 유저 예외 처리
        checkUser(userId);

        /// 유저의 관심 지역 목록 조회하기
        List<Favorite> regionList = favoritePort.loadUserFavoriteByType(userId, FavoriteType.REGION);

        /// 관심 지역 목록을 바탕으로 가격 조회하기
        List<String> regionCodes = regionList.stream()
                .map(Favorite::getRegionCode)
                .toList();

        /// 코드들을 바탕으로 조회하기
        return regionPricePort.loadRegionPriceByCodes(regionCodes);
    }


    /// 나의 관심 아파트 조회
    @Override
    public List<Estate> getFavoriteEstates(Long userId) {

        /// 유저 예외 처리
        checkUser(userId);

        /// 유저의 관심 목록 조회하기
        List<Favorite> estateList = favoritePort.loadUserFavoriteByType(userId, FavoriteType.APARTMENT);

        /// 유저 관련 아파트 조회
        List<String> codes = estateList.stream()
                .map(Favorite::getKaptCode)
                .toList();

        /// 코드들을 바탕으로 조회하기
        return estatePort.loadEstatesByCodes(codes);
    }


    @Override
    public List<EstatePrice> getFavoriteEstatePrice(Long userId) {

        /// 유저 예외 처리
        checkUser(userId);

        /// 유저의 관심 목록 조회하기
        List<Favorite> regionList = favoritePort.loadUserFavoriteByType(userId, FavoriteType.APARTMENT);

        /// 관심 아파트 목록을 바탕으로 가격 조회하기
        List<String> codes = regionList.stream()
                .map(Favorite::getKaptCode)
                .toList();

        log.info("codes: {}", codes);

        List<EstatePrice> list = estatePricePort.loadEstatePricesByCodes(codes);

        log.info("list: {}", list);
        return list;
    }


    /// 나의 소유 아파트 조회
    @Override
    public List<EstatePrice> getMyEstatePrices(Long userId) {

        /// 유저 예외 처리
        checkUser(userId);

        /// 나의 주거지 인증 아파트 목록 조회
        List<EstateOwnership> ownerships = estateOwnerShipPort.loadMyOwnerships(userId);

        List<String> codes = ownerships.stream()
                .map(EstateOwnership::getKaptCode)
                .toList();

        return estatePricePort.loadEstatePricesByCodes(codes);
    }


    /// 내부 함수
    private void checkUser(Long userId) {
        if (!userPort.checkExistingById(userId)) {
            throw new NoSuchElementException(ErrorCode.NOT_USER.getMessage());
        }
    }


}
