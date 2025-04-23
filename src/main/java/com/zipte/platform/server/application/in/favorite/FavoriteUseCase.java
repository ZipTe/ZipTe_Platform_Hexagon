package com.zipte.platform.server.application.in.favorite;

import com.zipte.platform.server.domain.favorite.Favorite;
import com.zipte.platform.server.domain.favorite.FavoriteType;

import java.util.List;

public interface FavoriteUseCase {

    // 특정 아파트에 대한 관심목록 저장
    Favorite createFavorite(Long userId, FavoriteType type, String code);

    // 특정 아파트에 대한 관심목록 해제
    void removeFavorite(Long userId, Long favoriteId);

    // 특정 지역에 대한 관심목록 저장

    // 특정 지역에 대한 관심목록 해제

    // 사용자의 관심 목록 조회하기
    List<String> getFavorite(Long userId);

}
