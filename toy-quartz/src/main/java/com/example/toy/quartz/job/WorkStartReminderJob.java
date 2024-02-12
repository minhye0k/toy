package com.example.toy.quartz.job;


import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import static com.example.toy.common.constant.AppConstant.TICKET_SEQ;

public class WorkStartReminderJob extends QuartzJobBean {

    private final static String START_REMIND_MESSAGE_TEN_MIN = "업무가 10분 후 시작돼요";

    @Override
    protected void executeInternal(JobExecutionContext context) {
        JobDetail jobDetail = context.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();

        Long ticketSeq = jobDataMap.getLongFromString(TICKET_SEQ);

        // TODO : 외부 API 호출을 통한 알람 로직

    }


}
