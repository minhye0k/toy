package com.example.toy.common.exception;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
@Component
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {


    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {

        log.error("async error : ", ex);

        String message = String.format("message : %s \nmethod : %s\n params : %s\n", ex.getMessage(), method.getName(), Arrays.toString(params));

        // TODO : implement 메신저 알람

    }
}
