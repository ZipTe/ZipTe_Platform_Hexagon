package com.zipte.platform.server.adapter.out.jpa.user;

import com.zipte.platform.server.adapter.out.jpa.BaseEntity;
import com.zipte.platform.server.domain.user.UserWeight;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserWeightJpaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                      // 아이디

    private Long userId;

    private int convenienceWeight;        // 편의시설 (마트, 병원 등 근접성)

    private int transportationWeight;     // 교통 (지하철, 버스 정류장까지 소요 시간)

    private int regionPreferenceWeight;   // 지역 선호도

    private int parkAccessWeight;         // 공원 접근성

    private int distanceToWorkWeight;     // 직장/학교와의 거리

    public static UserWeightJpaEntity from(UserWeight userWeight) {
        return UserWeightJpaEntity.builder()
                .userId(userWeight.getUserId())
                .convenienceWeight(userWeight.getConvenienceWeight())
                .transportationWeight(userWeight.getTransportationWeight())
                .regionPreferenceWeight(userWeight.getRegionPreferenceWeight())
                .parkAccessWeight(userWeight.getParkAccessWeight())
                .distanceToWorkWeight(userWeight.getDistanceToWorkWeight())
                .build();
    }

    public UserWeight toDomain() {
        return UserWeight.builder()
                .id(id)
                .userId(userId)
                .convenienceWeight(convenienceWeight)
                .transportationWeight(transportationWeight)
                .regionPreferenceWeight(regionPreferenceWeight)
                .parkAccessWeight(parkAccessWeight)
                .distanceToWorkWeight(distanceToWorkWeight)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }
}
