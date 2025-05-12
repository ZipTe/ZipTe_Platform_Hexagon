package com.zipte.platform.server.application.service;

import com.zipte.platform.core.response.ErrorCode;
import com.zipte.platform.server.adapter.in.web.dto.request.FavoriteRequest;
import com.zipte.platform.server.application.in.favorite.FavoriteUseCase;
import com.zipte.platform.server.application.out.estate.LoadEstatePort;
import com.zipte.platform.server.application.out.favorite.FavoritePort;
import com.zipte.platform.server.application.out.region.RegionPort;
import com.zipte.platform.server.application.out.user.UserPort;
import com.zipte.platform.server.domain.favorite.Favorite;
import com.zipte.platform.server.domain.favorite.FavoriteType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteService implements FavoriteUseCase {

    private final FavoritePort favoritePort;

    /// 외부 의존성
    private final UserPort loadUserPort;
    private final LoadEstatePort loadEstatePort;
    private final RegionPort loadRegionPort;

    @Override
    public Favorite createFavorite(FavoriteRequest request) {

        /// 유저가 존재하는지, 예외 처리
        if(!loadUserPort.checkExistingById(request.getUserId())) {
            throw new NoSuchElementException(ErrorCode.NOT_USER.getMessage());
        }

        /// 해당 코드에 대해서 적용된 것이 있는지, 예외처리
        boolean checked = favoritePort
                .checkFavoriteByUserIdAndTypeAndCode(request.getUserId(), request.getType(), request.getCode());

        if(checked) {
            throw new IllegalStateException(ErrorCode.BAD_REQUEST_ALREADY.getMessage());
        }

        /// 해당 코드가 존재하는지 예외 처리
        // 아파트 코드 예외처리
        if (request.getType().equals(FavoriteType.APARTMENT)){
            if (!loadEstatePort.checkExistingByCode(request.getCode())){
                throw new NoSuchElementException(ErrorCode.NOT_ESTATE.getMessage());
            }
        }

        // 지역 코드 예외처리
        if (request.getType().equals(FavoriteType.REGION)){
            if (!loadRegionPort.checkExistCode(request.getCode())) {
                throw new NoSuchElementException(ErrorCode.NOT_REGION.getMessage());
            }
        }

        /// 객체 생성
        Favorite favorite = Favorite.of(request.getUserId(), request.getType(), request.getCode());

        /// 저장하기
        return favoritePort.saveFavorite(favorite);
    }

    @Override
    public void removeFavorite(Long userId, Long favoriteId) {

        /// 유저가 존재하는지, 예외처리
        if(!loadUserPort.checkExistingById(userId)){
            throw new NoSuchElementException(ErrorCode.NOT_USER.getMessage());
        }

        /// 해당 유저와 favoriteId가 관련이 있는지 예외처리
        Favorite favorite = favoritePort.loadFavoriteByIdAndUserId(favoriteId, userId)
                .orElseThrow(() -> new NoSuchElementException(ErrorCode.NOT_FAVORITE_YOURS.getMessage()));

        /// 저장
        favoritePort.deleteFavorite(favorite.getId());
    }

    @Override
    public Page<Favorite> getFavorite(Long userId, Pageable pageable) {

        /// 유저가 존재하는지, 예외 처리
        if(!loadUserPort.checkExistingById(userId)) {
            throw new NoSuchElementException(ErrorCode.NOT_USER.getMessage());
        }

        /// 모든 데이터 가져오기
        return favoritePort.loadUserFavorite(userId, pageable);
    }

    @Override
    public Page<Favorite> getFavoriteByType(Long userId, FavoriteType type, Pageable pageable) {

        /// 유저가 존재하는지, 예외 처리
        if(!loadUserPort.checkExistingById(userId)) {
            throw new NoSuchElementException(ErrorCode.NOT_USER.getMessage());
        }

        return favoritePort.loadUserFavoriteByType(userId, type, pageable);
    }
}
