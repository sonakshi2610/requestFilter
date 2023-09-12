package com.craftdemo.requestfilter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = HttpClientErrorException.BadRequest.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseDto<?> handleException(HttpClientErrorException.BadRequest e) {
        log.error("Error occured in the request : {}", e.getMessage());
        return ResponseDto.builder()
                .error(ErrorDto.builder()
                        .message(e.getMessage())
                        .code(e.getStatusCode().name())
                        .build())
                .correlationId(RequestContext.getTraceId())
                .build();
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDto<?> handleException(Exception e) {
        log.error("Error occured in the request : {}", e.getMessage());
        return ResponseDto.builder()
                .error(ErrorDto.builder()
                        .message(e.getMessage())
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.name())
                        .build())
                .correlationId(RequestContext.getTraceId())
                .build();
    }
}
