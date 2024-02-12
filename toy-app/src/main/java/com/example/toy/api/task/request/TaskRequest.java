package com.example.toy.api.task.request;

import com.example.toy.task.domain.task.dto.TaskDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.wildfly.common.annotation.NotNull;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class TaskRequest {
    @NotNull
    private Long userSeq;

    @NotNull
    private Long requiredCount;

    @NotNull
    private String subject;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startAt;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endAt;

    public TaskDto toTaskDto() {
        return TaskDto.builder()
                .userSeq(userSeq)
                .requiredCount(requiredCount)
                .subject(subject)
                .startAt(startAt)
                .endAt(endAt)
                .build();
    }
}
