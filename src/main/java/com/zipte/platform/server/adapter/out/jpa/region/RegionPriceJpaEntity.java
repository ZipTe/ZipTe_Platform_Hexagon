package com.zipte.platform.server.adapter.out.jpa.region;

import com.zipte.platform.server.adapter.out.jpa.BaseEntity;
import com.zipte.platform.server.domain.region.RegionPrice;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class RegionPriceJpaEntity extends BaseEntity {

    @Id
    private String regionCode;

    private Double under15;

    private Double between15and20;

    private Double between20and30;

    private Double upper30;

    /// 정적 팩토리 메서드
    public static RegionPriceJpaEntity from(RegionPrice regionPrice) {
        return RegionPriceJpaEntity.builder()
                .regionCode(regionPrice.getRegionCode())
                .under15(regionPrice.getUnder15())
                .between15and20(regionPrice.getBetween15and20())
                .between20and30(regionPrice.getBetween20and30())
                .upper30(regionPrice.getUpper30())
                .build();
    }

    /// toDomain
    public RegionPrice toDomain(){
        return RegionPrice.builder()
                .regionCode(regionCode)
                .under15(under15)
                .between15and20(between15and20)
                .between20and30(between20and30)
                .upper30(upper30)
                .build();
    }


    /// 수정을 위한 메서드
    public void updateFromDomain(RegionPrice domain) {
        this.regionCode = domain.getRegionCode();
        this.under15 = domain.getUnder15();
        this.between15and20 = domain.getBetween15and20();
        this.between20and30 = domain.getBetween20and30();
        this.upper30 = domain.getUpper30();
    }



}
