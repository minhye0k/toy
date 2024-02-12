package com.example.toy.common.exception;

import org.springframework.validation.Errors;

public class GlobalException {
    public static class InvalidParameterException extends CustomException {

        private final Errors errors;

        public InvalidParameterException(Errors errors) {
            super(ExceptionCode.INVALID_PARAMETER);
            this.errors = errors;
        }

        public Errors getErrors(){
            return this.errors;
        }
    }
}
