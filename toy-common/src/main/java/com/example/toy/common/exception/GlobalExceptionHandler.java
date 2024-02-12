package com.example.toy.common.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;

import static org.springframework.http.HttpStatus.FORBIDDEN;


@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.InvalidParameterException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidParameterException(GlobalException.InvalidParameterException e) {
        ExceptionCode exceptionCode = e.getExceptionCode();

        ExceptionResponse exceptionResponse
                = ExceptionResponse
                .create()
                .status(exceptionCode.getStatus())
                .message(e.toString())
                .exceptions(e.getErrors());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<HashMap<String, String>> handleCustomException(CustomException e) {
        ExceptionCode exceptionCode = e.getExceptionCode();

        HashMap<String, String> message = new HashMap<>();
        message.put("message", exceptionCode.getMessage());
        message.put("code", exceptionCode.getCode());

        return new ResponseEntity<>(message, HttpStatus.resolve(exceptionCode.getStatus()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {

        // @PreAuthorize 가 Controller 레벨에 걸려있어서 발생하는 예외가 여기로 들어옴
        if (e instanceof AccessDeniedException) {
            ExceptionResponse exceptionResponse
                    = ExceptionResponse
                    .create()
                    .status(FORBIDDEN.value())
                    .message(FORBIDDEN.getReasonPhrase());

            return new ResponseEntity<>(exceptionResponse, FORBIDDEN);
        }

        ExceptionResponse exceptionResponse
                = ExceptionResponse
                .create()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.toString());

        log.error("Unexpected Error", e);

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}