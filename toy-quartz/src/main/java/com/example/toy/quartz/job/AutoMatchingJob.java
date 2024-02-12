package com.example.toy.quartz.job;

import com.example.toy.common.event.ticket.TicketProposalEvent;
import com.example.toy.common.util.ConvenienceUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.ArrayList;
import java.util.List;

import static com.example.toy.common.constant.AppConstant.TASK_SEQ;
import static com.example.toy.common.constant.AppConstant.USER_SEQS;


@Slf4j
@PersistJobDataAfterExecution
public class AutoMatchingJob extends QuartzJobBean {
    private final int PROPOSAL_COUNT = 5;
    private final int ZERO = 0;

    @Autowired
    private Scheduler scheduler;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        JobDetail jobDetail = context.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();

        Long taskSeq = jobDataMap.getLongFromString(TASK_SEQ);
        String userSeqsString = jobDataMap.getString(USER_SEQS);

        log.info(userSeqsString);
        List<Long> userSeqs = ConvenienceUtil.stringToLongList(userSeqsString);

        if (userSeqs.isEmpty()) {
            processEnd(jobDetail.getKey());
            return;
        }

        int userSeqsSize = userSeqs.size();

        if (PROPOSAL_COUNT >= userSeqsSize) {
            log.info("!?!?!!?");
            eventPublisher.publishEvent(
                    TicketProposalEvent
                            .builder()
                            .taskSeq(taskSeq)
                            .userSeqs(userSeqs)
                            .build()
            );
            processEnd(jobDetail.getKey());
            return;
        }

        eventPublisher.publishEvent(
                TicketProposalEvent
                        .builder()
                        .taskSeq(taskSeq)
                        .userSeqs(userSeqs.subList(ZERO, PROPOSAL_COUNT))
                        .build()
        );

        jobDataMap.put(USER_SEQS, new ArrayList<>(userSeqs.subList(PROPOSAL_COUNT, userSeqsSize)).toString());
    }

    private void processEnd(JobKey jobKey) {
        try {
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            log.error("auto matching scheduler end error", e);
        }
    }
}
