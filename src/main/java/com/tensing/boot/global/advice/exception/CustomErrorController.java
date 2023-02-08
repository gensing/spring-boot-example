package com.tensing.boot.global.advice.exception;

import com.tensing.boot.global.CommonResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomErrorController implements ErrorController {

    // https://velog.io/@gillog/Spring-404-Error-Custom%ED%95%98%EA%B8%B0-ExceptionHandler%EC%82%AC%EC%9A%A9-NoHandlerFoundException-Throw-%EC%95%88%EB%90%A0%EB%95%8C
    // https://tecoble.techcourse.co.kr/post/2021-11-24-spring-customize-unhandled-api/

    // HandlerExceptionResolver 확인해보기 - 현재는 에러 발생시 dispacther 에서 mdc가 소멸한다.

    // "/error" 매핑이 호출 되는 경우들을 확인하기. 현재 확인: spring 영역이 아닌 곳에서 일어나는 에러는 이곳을 탄다.
    @GetMapping(value = "/error", consumes = {MediaType.ALL_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CommonResponse<ErrorMessages>> error(HttpServletRequest request) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        int statusCode = status == null ? HttpStatus.OK.value() : Integer.valueOf(status.toString());
        String message = switch (HttpStatus.valueOf(statusCode)) {
            case OK -> "ERROR";
            case NOT_FOUND -> "NOT FOUND";
            case INTERNAL_SERVER_ERROR -> "INTERNAL SERVER ERROR";
            default -> String.valueOf(request.getAttribute(RequestDispatcher.ERROR_MESSAGE));
        };

        return ResponseEntity
                .status(statusCode)
                .body(CommonResponse.<ErrorMessages>builder()
                        .status(statusCode)
                        .body(ErrorMessages.of(message)) // status 받을 수 있게 변경.
                        .build());
    }

}