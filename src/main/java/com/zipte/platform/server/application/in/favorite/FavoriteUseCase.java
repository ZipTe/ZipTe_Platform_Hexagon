package com.zipte.platform.server.application.in.favorite;

import com.zipte.platform.server.adapter.in.web.dto.request.FavoriteRequest;
import com.zipte.platform.server.domain.favorite.Favorite;
import com.zipte.platform.server.domain.favorite.FavoriteType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FavoriteUseCase {

    // 특정 아파트,지역에 대한 관심목록 저장
    Favorite createFavorite(FavoriteRequest request);

    // 특정 아파트,지역에 대한 관심목록 해제
    void removeFavorite(Long userId, Long favoriteId);

    // 사용자의 관심 목록 조회하기
    Page<Favorite> getFavorite(Long userId, Pageable pageable);

    // 사용자의 관심 목록 조회하기
    Page<Favorite> getFavoriteByType(Long userId, FavoriteType type, Pageable pageable);


}
