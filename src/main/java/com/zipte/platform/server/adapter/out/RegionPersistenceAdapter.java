package com.zipte.platform.server.adapter.out;

import com.zipte.platform.server.adapter.out.jpa.region.RegionJpaEntity;
import com.zipte.platform.server.adapter.out.jpa.region.RegionJpaRepository;
import com.zipte.platform.server.application.out.region.RegionPort;
import com.zipte.platform.server.domain.region.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RegionPersistenceAdapter implements RegionPort {

    private final RegionJpaRepository repository;

    @Override
    public Optional<Region> loadRegion(String regionCode) {
        return repository.findByCode(regionCode)
                .map(RegionJpaEntity::toDomain);
    }

    @Override
    public List<Region> loadChildRegionsByPrefix(String prefix, String suffix) {

        // 정확한 패턴을 설정하여 바로 하위 지역만 가져오도록 수정
        String pattern;

        // 시군구 또는 시도일 경우
        if ("".equals(suffix)) {
            // 읍면동의 경우에는 %를 사용하여 중간 값을 포함시켜 조회
            pattern = prefix + "%%";  // 하위 읍면동 조회
        } else {
            pattern = prefix + "%" + suffix;  // 특정 값으로 채워진 패턴
        }

        return repository.findRegionsByPattern(pattern).stream()
                .map(RegionJpaEntity::toDomain)
                .toList();
    }
}
