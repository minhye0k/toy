package com.example.toy.common.annotation;

import org.springframework.scheduling.annotation.Async;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Async("eventAsyncExecutor")
public @interface EventAsync {
}
