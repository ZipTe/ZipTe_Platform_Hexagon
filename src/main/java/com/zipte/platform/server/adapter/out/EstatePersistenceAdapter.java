package com.zipte.platform.server.adapter.out;

import com.zipte.platform.server.adapter.out.mongo.estate.EstateDocument;
import com.zipte.platform.server.adapter.out.mongo.estate.EstateMongoRepository;
import com.zipte.platform.server.application.out.estate.LoadEstatePort;
import com.zipte.platform.server.domain.estate.Estate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EstatePersistenceAdapter implements LoadEstatePort {

    private final EstateMongoRepository repository;

    @Override
    public List<Estate> loadEstatesNearBy( double longitude, double latitude, double radiusInKm) {
        return repository.findByLocation(longitude, latitude,radiusInKm)
                .stream().map(EstateDocument::toDomain).toList();
    }

    @Override
    public Optional<Estate> loadEstateByCode(String kaptCode) {
        return repository.findByKaptCode(kaptCode)
                .map(EstateDocument::toDomain);
    }

    @Override
    public Optional<Estate> loadEstateByName(String kaptName) {
        return repository.findByKaptName(kaptName)
                .map(EstateDocument::toDomain);
    }

    @Override
    public Page<Estate> loadEstatesByRegion(String region, Pageable pageable) {
        return repository.findByKaptAddrContaining(region, pageable)
                .map(EstateDocument::toDomain);
    }

    @Override
    public boolean checkExistingByCode(String kaptCode) {
        return repository.existsByKaptCode(kaptCode);
    }

    @Override
    public List<Estate> loadEstatesByRegion(String region) {
        return repository.findByKaptAddrContaining(region).stream()
                .map(EstateDocument::toDomain)
                .toList();
    }

    @Override
    public List<Estate> loadEstatesByCodes(List<String> kaptCodes) {
        return repository.findByKaptCodeIn(kaptCodes).stream()
                .map(EstateDocument::toDomain)
                .toList();
    }
}
