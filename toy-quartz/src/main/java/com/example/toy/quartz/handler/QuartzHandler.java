package com.example.toy.quartz.handler;


import com.example.toy.common.util.DateUtil;
import com.example.toy.quartz.job.AutoMatchingJob;
import com.example.toy.quartz.job.WorkEndReminderJob;
import com.example.toy.quartz.job.WorkStartReminderJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static com.example.toy.common.constant.AppConstant.*;


@Slf4j
@Component
@RequiredArgsConstructor
public class QuartzHandler {
    private final String MATCHING = "MATCHING";
    private static final int AUTO_MATCHING_REPEAT_COUNT = 3;
    private static final int AUTO_MATCHING_REPEAT_INTERVAL = 10;

    private final Scheduler scheduler;

    public void addWorkStartReminderJob(Long ticketSeq, LocalDateTime startAt) {
        JobDetail jobDetail = JobBuilder.newJob(WorkStartReminderJob.class)
                .setJobData(new JobDataMap() {{
                    putAsString(TICKET_SEQ, ticketSeq);
                }})
                .withIdentity(ticketSeq.toString(), WORK_START_REMINDER)
                .build();

        try {
            scheduler.scheduleJob(
                    jobDetail,
                    oneOffTrigger(DateUtil.aMinutesBeforeFrom(startAt, 10), ticketSeq.toString(), WORK_START_REMINDER)
            );
        } catch (SchedulerException e) {
            log.error(e.toString());
        }
    }

    public void addWorkEndReminderJob(Long ticketSeq, LocalDateTime endAt) {
        JobDetail jobDetail = JobBuilder.newJob(WorkEndReminderJob.class)
                .setJobData(new JobDataMap() {{
                    putAsString(TICKET_SEQ, ticketSeq);
                }})
                .withIdentity(ticketSeq.toString(), WORK_END_REMINDER)
                .build();

        try {
            scheduler.scheduleJob(
                    jobDetail,
                    oneOffTrigger(DateUtil.aMinutesBeforeFrom(endAt, 10), ticketSeq.toString(), WORK_END_REMINDER)
            );
        } catch (SchedulerException e) {
            log.error(e.toString());
        }
    }

    public void addAutoMatchingJob(Long taskSeq, List<Long> userSeqs) {
        JobDetail jobDetail = JobBuilder.newJob(AutoMatchingJob.class)
                .setJobData(new JobDataMap() {{
                    putAsString(TASK_SEQ, taskSeq);
                    put(USER_SEQS, userSeqs.toString());
                }})
                .withIdentity(taskSeq.toString(), MATCHING)
                .build();

        try {
            scheduler.scheduleJob(
                    jobDetail,
                    autoMatchingTrigger(taskSeq.toString())
            );
        } catch (SchedulerException e) {
            log.error(e.toString());
        }
    }


    @Transactional
    public void removeAutoMatchingJob(Long requestSeq) {
        JobKey jobKey = JobKey.jobKey(requestSeq.toString(), MATCHING);
        removeJob(jobKey);
    }

    private void removeJob(JobKey jobKey) {
        try {
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            log.error(e.toString());
        }
    }

    private SimpleTrigger oneOffTrigger(Date startAt, String jobName, String groupName) {
        return TriggerBuilder.newTrigger()
                .withIdentity(jobName, groupName)
                .startAt(startAt)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0))
                .build();
    }

    private SimpleTrigger autoMatchingTrigger(String jobName) {

        return TriggerBuilder.newTrigger()
                .withIdentity(jobName, MATCHING)
                .startNow()
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(
                        AUTO_MATCHING_REPEAT_COUNT,
                        AUTO_MATCHING_REPEAT_INTERVAL)
                )
                .build();
    }

}
