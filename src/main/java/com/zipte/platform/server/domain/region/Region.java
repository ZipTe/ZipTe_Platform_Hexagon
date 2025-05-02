package com.zipte.platform.server.domain.region;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Region {

    // 코드
    private String code;

    // 부모 코드
    private String parentCode;

    // 법정동명
    private String name;

    public static Region of(String code, String parentCode, String name) {
        return Region.builder()
                .code(code)
                .parentCode(parentCode)
                .name(name)
                .build();
    }

}
