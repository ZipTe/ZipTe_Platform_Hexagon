package com.zipte.platform.server.application.out.favorite;

import com.zipte.platform.server.domain.favorite.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FavoritePort {

    // 관심 목록 저장
    Favorite saveFavorite(Favorite favorite);

    // 관심 목록 해제
    void deleteFavorite(Long favoriteId);

    // 관심 목록 조회하기
    Page<Favorite> loadUserPreferences(Long userId, Pageable pageable);

    // 아파트
    List<Long> loadUserFavoriteByComplexCode(String complexCode);


}
