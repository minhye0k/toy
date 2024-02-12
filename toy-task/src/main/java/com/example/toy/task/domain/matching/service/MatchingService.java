package com.example.toy.task.domain.matching.service;

import com.example.toy.common.constant.MatchingType;
import com.example.toy.core.task.entity.Task;

public interface MatchingService {
    void startMatching(Task task);

    MatchingType getType();
}
