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
    FORBIDDEN(403, HttpStatus.FORBIDDEN, "접속 권한이 없습니다."),
    // 404 Not Found
    NOT_FOUND_END_POINT(404, HttpStatus.NOT_FOUND, "요청한 대상이 존재하지 않습니다."),
    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."),

    /// 유저 관련
    NOT_USER(1000, HttpStatus.NOT_FOUND, "해당하는 유저가 존재하지 않습니다."),
    NOT_CONSENT(1010, HttpStatus.BAD_REQUEST, "유저 정보 동의가 체크되지 않았습니다"),
    FIRST_LOGIN(1200,HttpStatus.NOT_FOUND,"최초 로그인유저이기에 추가정보기입이 필요합니다."),

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
    BAD_REQUEST_DELETE_REVIEW(5001, HttpStatus.BAD_REQUEST, "본인이 등록하지 않은 리뷰를 삭제할 수 없습니다."),
    BAD_REQUEST_DUPLICATE_REVIEW(5002, HttpStatus.BAD_REQUEST, "이미 존재하는 리뷰가 있습니다. 수정을 진행해주세요"),
    BAD_REQUEST_REVIEW(5003, HttpStatus.BAD_REQUEST, "실거주자 인증이 되지 않은 유저입니다."),

    /// 관심목록
    NOT_FAVORITE(6000, HttpStatus.NOT_FOUND, "해당하는 관심목록이 존재하지 않습니다."),
    BAD_REQUEST_ALREADY(6001, HttpStatus.BAD_REQUEST, "이미 관심목록으로 지정된 대상입니다."),
    NOT_FAVORITE_YOURS(6002, HttpStatus.NOT_FOUND, "해당하는 코드가 유저의 관심목록에 존재하지 않습니다."),

    /// 질문 관련
    NOT_QUESTION(7000, HttpStatus.NOT_FOUND, "해당하는 질문이 존재하지 않습니다"),
    NOT_ANSWER(7001, HttpStatus.NOT_FOUND, "해당하는 답변이 존재하지 않습니다"),
    BAD_REQUEST_DELETE_QUESTION(7010, HttpStatus.BAD_REQUEST, "본인이 등록하지 않은 질문을 삭제할 수 없습니다."),
    BAD_REQUEST_DELETE_ANSWER(7011, HttpStatus.BAD_REQUEST, "본인이 등록하지 않은 질문을 삭제할 수 없습니다."),

    /// 인증 관련
    NOT_VALID(401, HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다."),
    NO_TOKEN(401, HttpStatus.UNAUTHORIZED, "토큰이 존재하지 않습니다."),;

    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;


    public static ErrorCode fromMessage(String message) {
        for (ErrorCode errorCode : ErrorCode.values()) {
            if (errorCode.getMessage().equals(message)) {
                return errorCode;
            }
        }
        throw new IllegalArgumentException("해당 message를 가진 ErrorCode가 존재하지 않습니다: " + message);
    }
}
