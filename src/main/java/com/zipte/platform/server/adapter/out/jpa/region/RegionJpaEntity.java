package com.zipte.platform.server.adapter.out.jpa.region;

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

    private String parentCode;

    private String name;

}
