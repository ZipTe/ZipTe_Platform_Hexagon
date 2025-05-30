package com.zipte.platform.server.adapter.out.jpa.region;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface RegionPriceJpaRepository extends JpaRepository<RegionPriceJpaEntity, Long> {

    Optional<RegionPriceJpaEntity> findByRegionCode(String regionCode);

    List<RegionPriceJpaEntity> findByRegionCodeIn(List<String> regionCodes);

    boolean existsByRegionCode(String regionCode);
}
