package com.example.toy.api.task.controller;

import com.example.toy.api.task.request.MatchRequest;
import com.example.toy.api.task.request.TaskRequest;
import com.example.toy.common.exception.GlobalException;
import com.example.toy.task.domain.task.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/tasks")
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Void> postTask(@Valid @RequestBody TaskRequest taskRequest,
                                         BindingResult bindingResult){
        if(bindingResult.hasErrors()) throw new GlobalException.InvalidParameterException(bindingResult);

        taskService.saveTask(taskRequest.toTaskDto());
        return ResponseEntity.ok().build();
    }

    @PostMapping("match")
    public ResponseEntity<Void> matchTheTask(@Valid @RequestBody MatchRequest matchRequest,
                                             BindingResult bindingResult){
        if(bindingResult.hasErrors()) throw new GlobalException.InvalidParameterException(bindingResult);

        taskService.match(matchRequest.toMatchDto());
        return ResponseEntity.ok().build();
    }

}
