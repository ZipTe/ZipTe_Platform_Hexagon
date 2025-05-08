package com.zipte.platform.server.adapter.out.jpa.region;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface RegionJpaRepository extends JpaRepository<RegionJpaEntity, String> {

    Optional<RegionJpaEntity> findByCode(String code);

    // 바로 아래 단계의 하위 지역 조회
    @Query("SELECT r FROM RegionJpaEntity r WHERE r.code LIKE :pattern")
    List<RegionJpaEntity> findRegionsByPattern(@Param("pattern") String pattern);


}
