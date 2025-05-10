package com.zipte.platform.core.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Test Error
    TEST_ERROR(100, HttpStatus.BAD_REQUEST, "테스트 에러입니다."),
    // 400 Bad Request
    BAD_REQUEST(400, HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    // 401 SC_UNAUTHORIZED
    SC_UNAUTHORIZED(401, HttpStatus.UNAUTHORIZED,"로그인이 필요합니다"),
    // 403 Bad Reques
    Forbidden(403, HttpStatus.FORBIDDEN, "접속 권한이 없습니다."),
    // 404 Not Found
    NOT_FOUND_END_POINT(404, HttpStatus.NOT_FOUND, "요청한 대상이 존재하지 않습니다."),
    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."),

    /// 유저 관련
    NOT_USER(1000, HttpStatus.NOT_FOUND, "해당하는 유저가 존재하지 않습니다."),

    /// 부동산 관련
    NOT_ESTATE(2000, HttpStatus.NOT_FOUND, "해당하는 부동산이 존재하지 않습니다."),
    NOT_ESTATE_IN_YOUR_AREA(2001, HttpStatus.BAD_REQUEST, "1KM 반경 내에 해당 부동산이 존재하지 않습니다."),
    NOT_DATE(2002, HttpStatus.BAD_REQUEST, "오늘 이후의 구매 일정은 설정할 수 없습니다."),
    BAD_REQUEST_ESTATE(400, HttpStatus.BAD_REQUEST, "이미 등록된 아파트를 등록할 수 없습니다,."),

    /// 지역 관련
    NOT_REGION(4000, HttpStatus.NOT_FOUND, "해당하는 지역이 존재하지 않습니다."),
    NOT_REGION_PRICE(4001,HttpStatus.NOT_FOUND, "해당하는 지역의 가격이 존재하지 않습니다."),

    /// 리뷰 관련
    NOT_REVIEW(5000, HttpStatus.NOT_FOUND, "해당하는 리뷰가 존재하지 않습니다."),
    BAD_REQUEST_REVIEW(5001, HttpStatus.BAD_REQUEST, "실거주자 인증이 되지 않은 유저입니다."),

    /// 관심목록
    NOT_FAVORITE(6000, HttpStatus.NOT_FOUND, "해당하는 관심목록이 존재하지 않습니다."),
    BAD_REQUEST_ALREADY(6001, HttpStatus.BAD_REQUEST, "이미 관심목록으로 지정된 대상입니다."),
    NOT_FAVORITE_YOURS(6002, HttpStatus.NOT_FOUND, "해당하는 코드가 유저의 관심목록에 존재하지 않습니다.");


    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;
}
