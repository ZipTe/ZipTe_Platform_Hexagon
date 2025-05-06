package com.zipte.platform.server.adapter.out.mongo.estate;


import com.zipte.platform.server.domain.estate.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "kaptPrice")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class EstatePriceDocument {

    private String id;

    private String kaptCode;

    private String kaptName;

    private String kaptAddr;

    private Location location;

    // 전용 면적 (㎡)
    private double exclusiveArea;

    // 거래 금액 (만원)
    private String price;

    // 거래 일자
    private String transactionDate;

}
