package com.zipte.platform.server.domain.estateOwnership;

import com.zipte.platform.server.domain.BaseDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EstateOwnership extends BaseDomain {

    private Long id;

    private Long userId;

    private String kaptCode;

    private LocalDateTime boughtAt;

    /// 정적 팩토리 메서드
    public static EstateOwnership of(Long userId, String kaptCode, LocalDateTime boughtAt) {
        return EstateOwnership.builder()
                .userId(userId)
                .kaptCode(kaptCode)
                .boughtAt(boughtAt)
                .build();
    }

}
