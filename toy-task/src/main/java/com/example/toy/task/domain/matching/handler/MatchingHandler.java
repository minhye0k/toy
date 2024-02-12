package com.example.toy.task.domain.matching.handler;

import com.example.toy.common.constant.MatchingType;
import com.example.toy.core.task.entity.Task;
import com.example.toy.task.domain.matching.service.MatchingService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class MatchingHandler {
    private final Map<MatchingType, MatchingService> matchingServiceByType;

    public MatchingHandler(List<MatchingService> matchingServices) {
        this.matchingServiceByType = matchingServices.stream().collect(Collectors.toMap(
                MatchingService::getType, Function.identity()
        ));
    }

    @Transactional
    public void match(Task task, MatchingType matchingType) {
        matchingServiceByType.get(matchingType).startMatching(task);
    }

}
