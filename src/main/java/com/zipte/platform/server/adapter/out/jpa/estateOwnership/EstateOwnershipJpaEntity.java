package com.zipte.platform.server.adapter.out.jpa.estateOwnership;

import com.zipte.platform.server.adapter.out.jpa.BaseEntity;
import com.zipte.platform.server.domain.estateOwnership.EstateOwnership;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EstateOwnershipJpaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String kaptCode;

    private LocalDateTime boughtAt;

    /// 정적 팩토리 메서드
    public static EstateOwnershipJpaEntity from(EstateOwnership domain) {
        return EstateOwnershipJpaEntity.builder()
                .userId(domain.getUserId())
                .kaptCode(domain.getKaptCode())
                .boughtAt(domain.getBoughtAt())
                .build();

    }

    /// 도메인
    public EstateOwnership toDomain() {
        return EstateOwnership.builder()
                .id(id)
                .userId(userId)
                .kaptCode(kaptCode)
                .boughtAt(boughtAt)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }

}
