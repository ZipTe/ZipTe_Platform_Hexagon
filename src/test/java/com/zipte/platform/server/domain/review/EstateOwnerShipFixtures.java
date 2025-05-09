package com.zipte.platform.server.domain.review;

import com.zipte.platform.server.domain.estateOwnership.EstateOwnership;

import java.time.LocalDateTime;

public class EstateOwnerShipFixtures {

    private static EstateOwnership estateOwnership(Long userId, String kaptCode, LocalDateTime boughtAt) {
        return EstateOwnership.builder()
                .id(1L)
                .userId(userId)
                .kaptCode(kaptCode)
                .boughtAt(boughtAt)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static EstateOwnership stub(Long userId, String kaptCode, LocalDateTime boughtAt) {
        return estateOwnership(userId, kaptCode, boughtAt);
    }

}
