package com.example.toy.task.domain.task.service;

import com.example.toy.common.constant.MatchingType;
import com.example.toy.common.event.matching.MatchingEvent;
import com.example.toy.common.exception.CustomException;
import com.example.toy.common.exception.ExceptionCode;
import com.example.toy.core.task.dao.TaskRepository;
import com.example.toy.core.task.entity.Task;
import com.example.toy.core.task.entity.TaskStatus;
import com.example.toy.task.domain.matching.handler.MatchingHandler;
import com.example.toy.task.domain.task.dto.MatchDto;
import com.example.toy.task.domain.task.dto.TaskDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final ApplicationEventPublisher eventPublisher;
    private final TaskRepository taskRepository;
    private final MatchingHandler matchingHandler;


    @Transactional
    public void saveTask(TaskDto taskDto) {
        Task task = Task.builder()
                .userSeq(taskDto.userSeq())
                .requiredCount(taskDto.requiredCount())
                .subject(taskDto.subject())
                .startAt(taskDto.startAt())
                .endAt(taskDto.endAt())
                .status(TaskStatus.RECEIPTED)
                .build();

        taskRepository.save(task);
    }

    @Transactional
    public void match(MatchDto matchDto) {
        Long seq = matchDto.seq();
        Task task = taskRepository.findById(seq)
                .orElseThrow(() -> new CustomException(ExceptionCode.TASK_NOT_FOUND));

        MatchingType matchingType = matchDto.matchingType();
        matchingHandler.match(task, matchingType);

        task.updateStatus(TaskStatus.MATCHING);

        eventPublisher.publishEvent(
                MatchingEvent.builder()
                .taskSeq(seq)
                .build()
        );
    }
}
