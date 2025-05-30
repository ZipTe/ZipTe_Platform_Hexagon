package com.zipte.platform.server.adapter.out.jpa.favorite;

import com.zipte.platform.server.domain.favorite.FavoriteType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FavoriteJpaRepository extends JpaRepository<FavoriteJpaEntity, Long> {

    Page<FavoriteJpaEntity> findByUserId(Long userId, Pageable pageable);

    List<FavoriteJpaEntity> findUserIdsByKaptCode(String kaptCode);

    Page<FavoriteJpaEntity> findByUserIdAndType(Long userId, FavoriteType type, Pageable pageable);

    List<FavoriteJpaEntity> findByUserIdAndType(Long userId, FavoriteType type);

    Optional<FavoriteJpaEntity> findByIdAndUserId(Long id, Long userId);

    boolean existsByUserIdAndKaptCode(Long userId, String kaptCode);

    boolean existsByUserIdAndRegionCode(Long userId, String regionCode);

}
