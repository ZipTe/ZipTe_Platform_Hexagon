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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class DashBoardService implements DashBoardUseCase {


    private final UserPort userPort;

    private final FavoritePort favoritePort;

    private final RegionPort regionPort;

    private final RegionPricePort regionPricePort;

    private final LoadEstatePort loadEstatePort;

    private final EstatePricePort estatePricePort;

    private final EstateOwnerShipPort estateOwnerShipPort;

    /// 나의 관심 지역 조회
    @Override
    public List<Region> getFavoriteRegion(Long userId) {
        /// 유저 예외 처리
        if (userPort.checkExistingById(userId)) {
            throw new NoSuchElementException(ErrorCode.NOT_USER.getMessage());
        }

        /// 유저의 관심 지역 목록 조회하기
        List<Favorite> regionList = favoritePort.loadUserFavoriteByType(userId, FavoriteType.REGION);


        /// 관심 지역 목록을 바탕으로 조회하기
        List<Region> response = new ArrayList<>();


        regionList.forEach(favorite -> {
            String regionCode = favorite.getRegionCode();
            Region region = regionPort.loadRegion(regionCode)
                    .orElseThrow(() -> new NoSuchElementException(ErrorCode.NOT_REGION.getMessage()));

            response.add(region);
        });

        return response;
    }

    @Override
    public List<RegionPrice> getFavoriteRegionPrices(Long userId) {

        /// 유저 예외 처리
        if (userPort.checkExistingById(userId)) {
            throw new NoSuchElementException(ErrorCode.NOT_USER.getMessage());
        }

        /// 유저의 관심 지역 목록 조회하기
        List<Favorite> regionList = favoritePort.loadUserFavoriteByType(userId, FavoriteType.REGION);

        /// 관심 지역 목록을 바탕으로 가격 조회하기
        List<RegionPrice> response = new ArrayList<>();

        regionList.forEach(region -> {
            String regionCode = region.getRegionCode();
            RegionPrice regionPrice = regionPricePort.loadRegionPriceByCode(regionCode)
                    .orElseThrow(() -> new NoSuchElementException(ErrorCode.NOT_REGION_PRICE.getMessage()));

            response.add(regionPrice);
        });

        return response;
    }


    /// 나의 관심 아파트 조회
    @Override
    public List<Estate> getFavoriteEstates(Long userId) {
        /// 유저 예외 처리
        if (userPort.checkExistingById(userId)) {
            throw new NoSuchElementException(ErrorCode.NOT_USER.getMessage());
        }

        /// 유저의 관심 목록 조회하기
        List<Favorite> estateList = favoritePort.loadUserFavoriteByType(userId, FavoriteType.APARTMENT);

        /// 유저 관련 아파트 조회
        List<Estate> response = new ArrayList<>();


        estateList.forEach(favorite -> {
            String kaptCode = favorite.getKaptCode();
            Estate estate = loadEstatePort.loadEstateByCode(kaptCode)
                    .orElseThrow(() -> new NoSuchElementException(ErrorCode.NOT_ESTATE.getMessage()));
            response.add(estate);
        });

        return response;
    }


    @Override
    public List<EstatePrice> getFavoriteEstatePrice(Long userId) {

        /// 유저 예외 처리
        if (userPort.checkExistingById(userId)) {
            throw new NoSuchElementException(ErrorCode.NOT_USER.getMessage());
        }

        /// 유저의 관심 목록 조회하기
        List<Favorite> regionList = favoritePort.loadUserFavoriteByType(userId, FavoriteType.APARTMENT);

        /// 관심 아파트 목록을 바탕으로 가격 조회하기
        // flatMap을 통해 새롭게 정의 해봄
        return regionList.stream()
                .map(Favorite::getKaptCode)
                .map(estatePricePort::loadAllEstatePrices)
                .flatMap(List::stream)
                .toList();
    }


    /// 나의 소유 아파트 조회
    @Override
    public List<EstatePrice> getMyEstatePrices(Long userId) {

        /// 유저 예외 처리
        if (userPort.checkExistingById(userId)) {
            throw new NoSuchElementException(ErrorCode.NOT_USER.getMessage());
        }

        /// 나의 주거지 인증 아파트 목록 조회
        List<EstateOwnership> ownerships = estateOwnerShipPort.loadMyOwnerships(userId);

        return ownerships.stream()
                .map(EstateOwnership::getKaptCode)
                .map(estatePricePort::loadAllEstatePrices)
                .flatMap(List::stream)
                .toList();
    }
}
