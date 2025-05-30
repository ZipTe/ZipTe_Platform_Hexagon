package com.zipte.platform.server.application.out.favorite;

import com.zipte.platform.server.domain.favorite.Favorite;
import com.zipte.platform.server.domain.favorite.FavoriteType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface FavoritePort {

    /// 생성
    // 관심 목록 저장
    Favorite saveFavorite(Favorite favorite);


    /// 조회
    // 상세 조회하기
    Optional<Favorite> loadFavoriteById(Long id);

    Optional<Favorite> loadFavoriteByIdAndUserId(Long id, Long userId);

    // 관심 목록 조회하기
    Page<Favorite> loadUserFavorite(Long userId, Pageable pageable);

    // 관심 목록 조회하기
    Page<Favorite> loadUserFavoriteByType(Long userId, FavoriteType type, Pageable pageable);

    List<Favorite> loadUserFavoriteByType(Long userId, FavoriteType type);

    // 중복 여부 판단 조회
    boolean checkFavoriteByUserIdAndTypeAndCode(Long userId, FavoriteType type, String code);



    /// 삭제
    // 관심 목록 해제
    void deleteFavorite(Long favoriteId);





    /// 외부에서 사용하는 코드
    /*
        알림센터 추가할 때 사용
     */
    List<Long> loadUserFavoriteByComplexCode(String complexCode);


}
