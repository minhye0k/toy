package com.example.toy.task.domain.task.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TaskDto(long requiredCount,
                      String subject,
                      LocalDateTime startAt,
                      LocalDateTime endAt,
                      Long userSeq) {

}
