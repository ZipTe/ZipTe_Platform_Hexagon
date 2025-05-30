package com.zipte.platform.server.adapter.out;

import com.zipte.platform.server.adapter.out.jpa.region.RegionPriceJpaEntity;
import com.zipte.platform.server.adapter.out.jpa.region.RegionPriceJpaRepository;
import com.zipte.platform.server.application.out.region.RegionPricePort;
import com.zipte.platform.server.domain.region.RegionPrice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RegionPricePersistenceAdapter implements RegionPricePort {

    private final RegionPriceJpaRepository repository;


    @Override
    public RegionPrice saveRegionPrice(RegionPrice regionPrice) {
        var entity = RegionPriceJpaEntity.from(regionPrice);

        return repository.save(entity)
                .toDomain();
    }

    @Override
    public void updateRegionPrice(RegionPrice newRegionPrice) {
        RegionPriceJpaEntity jpa = repository.findByRegionCode(newRegionPrice.getRegionCode())
                .orElseThrow(() -> new NoSuchElementException("RegionPrice not found"));

        jpa.updateFromDomain(newRegionPrice);

        repository.save(jpa);

    }



    @Override
    public Optional<RegionPrice> loadRegionPriceByCode(String regionCode) {

        return repository.findByRegionCode(regionCode)
                .map(RegionPriceJpaEntity::toDomain);
    }

    @Override
    public boolean checkRegionPriceExist(String regionCode) {
        return repository.existsByRegionCode(regionCode);
    }

    @Override
    public List<RegionPrice> loadRegionPriceByCodes(List<String> regionCodes) {
        return repository.findByRegionCodeIn(regionCodes).stream()
                .map(RegionPriceJpaEntity::toDomain)
                .toList();
    }

    @Override
    public void deleteRegionPriceByCode(String regionCode) {

    }
}
