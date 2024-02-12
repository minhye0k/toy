package com.example.toy.task.domain.matching.service;

import com.example.toy.common.constant.MatchingType;
import com.example.toy.core.task.entity.Task;
import com.example.toy.core.user.dao.UserRepository;
import com.example.toy.quartz.handler.QuartzHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AutoMatchingService implements MatchingService{
    private final UserRepository userRepository;
    private final QuartzHandler quartzHandler;


    @Transactional
    @Override
    public void startMatching(Task task) {
        List<Long> proposableUserSeqs = userRepository.findSeqsByProposableIs(true);

        quartzHandler.addAutoMatchingJob(task.getSeq(), proposableUserSeqs);
    }

    @Override
    public MatchingType getType() {
        return MatchingType.AUTO;
    }
}
