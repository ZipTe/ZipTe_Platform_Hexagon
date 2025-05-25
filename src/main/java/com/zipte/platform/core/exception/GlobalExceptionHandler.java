package com.zipte.platform.core.exception;

import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.core.response.CustomException;
import com.zipte.platform.core.response.ErrorCode;
import com.zipte.platform.core.response.ExceptionDto;
import com.zipte.platform.server.application.service.exception.AlreadyExistingEstateException;
import com.zipte.platform.server.application.service.exception.DuplicationUserException;
import com.zipte.platform.server.application.service.exception.NotExistingEstateInYourAreaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /// 공통 처리 메서드
    private ApiResponse<ExceptionDto> handleCustomException(CustomException customException, Exception e) {
        log.info("에러 로그 : {}", e.getMessage());

        return ApiResponse.fail(customException);
    }

    /// 예외 처리
    // 최하위 예외처리
    @ExceptionHandler(Exception.class)
    public ApiResponse<ExceptionDto> handleException(Exception e) {

        // 예외 클래스 생성
        CustomException exception = new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage());
        return handleCustomException(exception, e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<ExceptionDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        // 검증 에러 메시지 출력
        String errorMsg = e.getBindingResult().getFieldError().getDefaultMessage();

        /// 해당 예외 코드로 예외 처리
        CustomException exception = new CustomException(ErrorCode.BAD_PARAMETER, errorMsg);

        return handleCustomException(exception, e);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ApiResponse<ExceptionDto> handleNoSuchElementException(NoSuchElementException e) {

        /// 메세지 바탕으로 예외 코드 검색
        ErrorCode errorCode = ErrorCode.fromMessage(e.getMessage());

        /// 해당 예외 코드로 예외 처리
        CustomException exception = new CustomException(errorCode, null);

        return handleCustomException(exception, e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            IllegalStateException.class, IllegalArgumentException.class, AlreadyExistingEstateException.class,
            DuplicationUserException.class, NotExistingEstateInYourAreaException.class
    })
    public ApiResponse<ExceptionDto> handleIllegalStateException(Exception e) {

        /// 메세지 바탕으로 예외 코드 검색
        ErrorCode errorCode = ErrorCode.fromMessage(e.getMessage());

        /// 해당 예외 코드로 예외 처리
        CustomException exception = new CustomException(errorCode, null);

        return handleCustomException(exception, e);
    }



}
