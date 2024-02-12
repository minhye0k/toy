package com.example.toy.quartz.listener;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobListener implements org.quartz.JobListener {

    @Override
    public String getName() {
        return "quartz-job-listener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        log.info("jobToBeExecuted: " + context.getJobDetail().getKey().getName());
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {

    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        log.info("jobWasExecuted: " + context.getJobDetail().getKey().getName());
    }
}

