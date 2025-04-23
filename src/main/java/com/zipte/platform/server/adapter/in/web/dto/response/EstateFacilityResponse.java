package com.zipte.platform.server.adapter.in.web.dto.response;

import com.zipte.platform.server.domain.estate.Estate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EstateFacilityResponse {

    // 주변 시설 정보
    private String publicOffices; // 관공서, 행정기관
    private String hospitals; // 병원
    private String shoppingCenters; // 쇼핑몰, 백화점
    private String parks; // 공원

    // 교육 시설
    private String elementarySchool;
    private String middleSchool;
    private String highSchool;

    // 주거 편의 정보
    private String totalHouseholds; // 총 세대수
    private String householdUnits; // 세대 구성 수

    // 교통 정보
    private String busCommuteTime; // 버스 이동 시간
    private String subwayCommuteTime; // 지하철 이동 시간
    private String subwayLine; // 지하철 노선
    private String subwayStation; // 지하철역

    // 복지 시설
    private String welfareFacilities; // 노인정, 놀이터 등

    public static EstateFacilityResponse from(Estate estate) {
        return EstateFacilityResponse.builder()
                .publicOffices(extractFacility(estate.getConvenientFacility(), "관공서"))
                .hospitals(extractFacility(estate.getConvenientFacility(), "병원"))
                .shoppingCenters(extractFacility(estate.getConvenientFacility(), "백화점"))
                .parks(extractFacility(estate.getConvenientFacility(), "공원"))

                .elementarySchool(extractFacility(estate.getEducationFacility(), "초등학교"))
                .middleSchool(extractFacility(estate.getEducationFacility(), "중학교"))
                .highSchool(extractFacility(estate.getEducationFacility(), "고등학교"))

                .totalHouseholds(estate.getKaptdPcnt())
                .householdUnits(estate.getKaptdPcntu())

                .busCommuteTime(estate.getKaptdWtimebus())
                .subwayCommuteTime(estate.getKaptdWtimesub())
                .subwayLine(estate.getSubwayLine())
                .subwayStation(estate.getSubwayStation())

                .welfareFacilities(estate.getWelfareFacility())
                .build();
    }

    // 특정 유형의 시설을 추출하는 유틸리티 메서드
    private static String extractFacility(String facilities, String type) {
        if (facilities == null || !facilities.contains(type)) {
            return null;
        }
        for (String facility : facilities.split(" ")) {
            if (facility.startsWith(type)) {
                return facility;
            }
        }
        return null;
    }
}
