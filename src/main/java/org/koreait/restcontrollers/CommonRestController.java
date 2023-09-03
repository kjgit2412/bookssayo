package org.koreait.restcontrollers;

import org.koreait.commons.CommonException;
import org.koreait.commons.rests.JSONData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 공통적인 예외 처리를 담당하는 REST 컨트롤러
 */
@RestControllerAdvice("org.koreait.restcontrollers")
public class CommonRestController {

    /**
     * 예외 처리 메서드
     * @param e 발생한 예외 객체
     * @return 예외 정보를 담은 JSON 응답 데이터
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<JSONData<Object>> errorHandler(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (e instanceof CommonException) {
            CommonException commonException = (CommonException) e;
            status = commonException.getStatus();
        }

        JSONData<Object> jsonData = JSONData.builder()
                .success(false)
                .message(e.getMessage())
                .status(status)
                .build();

        return ResponseEntity.status(status).body(jsonData);
    }
}
