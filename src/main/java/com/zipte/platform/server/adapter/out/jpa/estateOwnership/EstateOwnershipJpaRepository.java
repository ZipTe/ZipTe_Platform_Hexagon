package com.zipte.platform.server.adapter.out.jpa.estateOwnership;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EstateOwnershipJpaRepository extends JpaRepository<EstateOwnershipJpaEntity, Long> {

    boolean existsByKaptCodeAndUserId(String kaptCode, Long userId);

    void deleteByKaptCodeAndUserId(String kaptCode, Long userId);
}
