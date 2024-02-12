package com.example.toy.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public enum ExceptionCode {
    INVALID_PARAMETER(400, null, "유효하지 않은 요청입니다."),
    INVALID_USER_INFORMATION(409, "USER-001", "잘못 된 유저 정보입니다."),
    TASK_NOT_FOUND(404, "TASK-001", "해당 업무 요청서를 찾을 수 없습니다."),
    TASK_IS_NOT_MATCHING(409, "TASK-002", "해당 업무는 접수 마감되었습니다."),
    TICKET_NOT_FOUND(404, "TICKET-001", "해당 업무 제안을 찾을 수 없습니다.");

    private int status;
    private String code;
    private String message;

}