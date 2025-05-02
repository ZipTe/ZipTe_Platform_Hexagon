package com.zipte.platform.server.adapter.out;

import com.zipte.platform.server.adapter.out.jpa.region.RegionJpaEntity;
import com.zipte.platform.server.adapter.out.jpa.region.RegionJpaRepository;
import com.zipte.platform.server.application.out.region.RegionPort;
import com.zipte.platform.server.domain.region.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RegionJpaPersistenceAdapter implements RegionPort {

    private final RegionJpaRepository repository;

    @Override
    public Region loadRegion(String regionCode) {
        return repository.findByCode(regionCode)
                .toDomain();
    }

    @Override
    public List<Region> loadChildRegionsByPrefix(String prefix) {
        return repository.findChildRegionsByPrefix(prefix).stream()
                .map(RegionJpaEntity::toDomain)
                .toList();
    }
}
