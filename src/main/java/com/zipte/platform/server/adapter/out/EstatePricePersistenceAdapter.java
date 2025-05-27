package com.zipte.platform.server.adapter.out;

import com.zipte.platform.server.adapter.out.mongo.estate.EstatePriceDocument;
import com.zipte.platform.server.adapter.out.mongo.estate.EstatePriceMongoRepository;
import com.zipte.platform.server.application.out.estate.EstatePricePort;
import com.zipte.platform.server.domain.estate.EstatePrice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EstatePricePersistenceAdapter implements EstatePricePort {

    private final EstatePriceMongoRepository repository;

    @Override
    public Optional<EstatePrice> loadRecentPriceByKaptCode(String kaptCode) {
        return repository.findFirstByKaptCodeOrderByTransactionDateDesc(kaptCode)
                .map(EstatePriceDocument::toDomain);
    }

    @Override
    public List<EstatePrice> loadAllEstatePrices(String kaptCode) {
        return repository.findAllByKaptCode(kaptCode).stream()
                .map(EstatePriceDocument::toDomain)
                .toList();
    }

    @Override
    public List<EstatePrice> loadEstatePriceByCodeAndArea(String kaptCode, double exclusiveArea) {
        return repository.findAllByKaptCodeAndExclusiveArea(kaptCode, exclusiveArea).stream()
                .map(EstatePriceDocument::toDomain)
                .toList();
    }

    @Override
    public List<EstatePrice> loadAllEstatePricesBetween(String kaptCode, String from, String to) {
        return List.of();
    }

    @Override
    public List<EstatePrice> loadEstatePricesByCodes(List<String> kaptCodes) {
        return repository.findByKaptCodeIn(kaptCodes).stream()
                .map(EstatePriceDocument::toDomain)
                .toList();
    }
}
