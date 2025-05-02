package com.zipte.platform.server.adapter.out.jpa.region;

import com.zipte.platform.server.domain.region.Region;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegionJpaEntity {

    @Id
    private String code;

    private String address;

    public static RegionJpaEntity from(Region region) {
        return RegionJpaEntity.builder()
                .code(region.getCode())
                .address(region.getAddress())
                .build();
    }

    public Region toDomain() {
        return Region.builder()
                .code(code)
                .address(address)
                .build();
    }

}
