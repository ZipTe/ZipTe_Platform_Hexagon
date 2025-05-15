package com.zipte.platform.server.adapter.out.jpa.region;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegionPriceJpaRepository extends JpaRepository<RegionPriceJpaEntity, Long> {

    Optional<RegionPriceJpaEntity> findByRegionCode(String regionCode);

    boolean existsByRegionCode(String regionCode);
}
