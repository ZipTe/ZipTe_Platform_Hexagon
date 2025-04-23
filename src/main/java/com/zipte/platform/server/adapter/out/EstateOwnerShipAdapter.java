package com.zipte.platform.server.adapter.out;

import com.zipte.platform.server.adapter.out.jpa.estateOwnership.EstateOwnershipJpaEntity;
import com.zipte.platform.server.adapter.out.jpa.estateOwnership.EstateOwnershipJpaRepository;
import com.zipte.platform.server.application.out.estateOwnership.EstateOwnerShipPort;
import com.zipte.platform.server.domain.estateOwnership.EstateOwnership;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EstateOwnerShipAdapter implements EstateOwnerShipPort {

    private final EstateOwnershipJpaRepository repository;

    @Override
    public EstateOwnership saveOwnership(EstateOwnership estateOwnership) {

        var entity = EstateOwnershipJpaEntity.from(estateOwnership);

        return repository.save(entity)
                .toDomain();

    }

    @Override
    public boolean loadOwnershipByUser(Long userId, String kaptCode) {
        return repository.existsByKaptCodeAndUserId(kaptCode, userId);
    }

    @Override
    public void deleteOwnership(Long userId, String kaptCode) {
        repository.deleteByKaptCodeAndUserId(kaptCode, userId);
    }

}
