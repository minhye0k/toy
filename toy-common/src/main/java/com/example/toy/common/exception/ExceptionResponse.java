package com.example.toy.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ExceptionResponse {
    private String code;

    private String message;

    private int status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("exceptions")
    private List<CustomFieldException> customFieldExceptions;

    public ExceptionResponse(){
    }

    static public ExceptionResponse create(){
        return new ExceptionResponse();
    }

    public ExceptionResponse code(String code){
        this.code = code;
        return this;
    }

    public ExceptionResponse status(int status){
        this.status = status;
        return this;
    }

    public ExceptionResponse message(String message){
        this.message = message;
        return this;
    }
    public ExceptionResponse exceptions(Errors errors){
        setCustomFieldExceptions(errors.getFieldErrors());
        return this;
    }


    public void setCustomFieldExceptions(List<FieldError> filedExceptions){
        customFieldExceptions = new ArrayList<>();

        filedExceptions.forEach(exception -> {
            customFieldExceptions.add(new CustomFieldException(
                    exception.getField(),
                    exception.getRejectedValue(),
                    exception.getDefaultMessage()
            ));
        });
    }

    @Getter
    @AllArgsConstructor
    public static class CustomFieldException{
        private String field;
        private Object value;
        private String reason;


    }
}
