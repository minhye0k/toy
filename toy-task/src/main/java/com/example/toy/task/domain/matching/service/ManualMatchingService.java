package com.example.toy.task.domain.matching.service;

import com.example.toy.common.constant.MatchingType;
import com.example.toy.core.task.entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ManualMatchingService implements MatchingService{

    @Transactional
    @Override
    public void startMatching(Task task) {
    }

    @Override
    public MatchingType getType() {
        return MatchingType.MANUAL;
    }
}
