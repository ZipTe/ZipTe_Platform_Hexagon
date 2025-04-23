package com.zipte.platform.server.adapter.in.web.dto.request;

import com.zipte.platform.server.domain.property.PropertyType;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PropertyRequest {

    // 위치 정보
    @NotBlank(message = "상세 주소는 필수입니다.")
    private String detailAddress;

    @Pattern(regexp = "\\d{5}", message = "우편번호는 5자리 숫자로 입력해야 합니다.")
    private String postalCode;

    // 유저 정보
    @NotNull(message = "사용자 ID는 필수입니다.")
    private Long userId;

    // 원하는 가격
    @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
    private long price;

    @NotBlank(message = "아파트 코드은 필수입니다.")
    private String aptCode;

    // 매물의 특징
    @NotNull(message = "매물 유형은 필수입니다.")
    private PropertyType type;

    @NotBlank(message = "매물 설명은 필수입니다.")
    private String description;

    @Min(value = 1, message = "방 개수는 1개 이상이어야 합니다.")
    private int quantity;

    @Min(value = 1, message = "욕실 개수는 1개 이상이어야 합니다.")
    private int bathrooms;

    @Min(value = 1900, message = "건축 연도가 올바르지 않습니다.")
    @Max(value = 2100, message = "건축 연도가 올바르지 않습니다.")
    private int builtYear;
}
