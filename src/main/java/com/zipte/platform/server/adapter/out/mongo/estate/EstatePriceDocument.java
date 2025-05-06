package com.zipte.platform.server.adapter.out.mongo.estate;

import com.zipte.platform.server.domain.estate.EstatePrice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "kaptPrice")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class EstatePriceDocument {

    @Field("_id")
    private String id;

    @Field("단지코드")
    private String kaptCode;

    @Field("단지명")
    private String kaptName;

    @Field("주소정보")
    private String kaptAddr;

    @Field("층")
    private int floor; // 새로 추가해야 함

    @Field("전용 면적 (㎡)")
    private double exclusiveArea;

    @Field("거래 금액 (만원)")
    private String price;

    @Field("거래 일자")
    private String transactionDate;

    /// 정적 팩토리 메소드
    public static EstatePriceDocument from(EstatePrice estatePrice) {

        return EstatePriceDocument.builder()
                .id(estatePrice.getId())
                .kaptCode(estatePrice.getKaptCode())
                .kaptName(estatePrice.getKaptName())
                .kaptAddr(estatePrice.getKaptAddr())
                .exclusiveArea(estatePrice.getExclusiveArea())
                .floor(estatePrice.getFloor())
                .price(estatePrice.getPrice())
                .transactionDate(estatePrice.getTransactionDate())
                .build();
    }

    public EstatePrice toDomain() {

        return EstatePrice.builder()
                .id(id)
                .kaptCode(kaptCode)
                .kaptName(kaptName)
                .kaptAddr(kaptAddr)
                .exclusiveArea(exclusiveArea)
                .price(price)
                .floor(floor)
                .transactionDate(transactionDate)
                .build();
    }

}
