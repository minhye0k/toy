package com.example.toy.alarm.scheduler;

import com.example.toy.alarm.service.AlarmService;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
@Slf4j
public class AlarmScheduler {
    private final AlarmService alarmService;

    private final static Map<Long, Long> unreadCountByUserSeq = new ConcurrentHashMap<>();

    private final static long ALARM_UNREAD_COUNT_UPDATE_RATE = 1000 * 1L;

    public static void increaseAlarmUnreadCount(Long userSeq){
        long unreadCount = unreadCountByUserSeq.getOrDefault(userSeq, 0L) + 1;
        unreadCountByUserSeq.put(userSeq, unreadCount);
    }

    @Scheduled(fixedDelay = ALARM_UNREAD_COUNT_UPDATE_RATE)
    public void increaseAlarmUnreadCount() {
        flushAlarmUnreadCount();
    }

    @PreDestroy
    public void flushRemainingAlarmUnreadCount(){
        flushAlarmUnreadCount();

        if(!unreadCountByUserSeq.isEmpty()){
            log.error("Alarm unread count update failed with elements : {}", unreadCountByUserSeq);
        }
    }

    private void flushAlarmUnreadCount(){
        for(Long userSeq : unreadCountByUserSeq.keySet()){
            Long unreadCount = unreadCountByUserSeq.remove(userSeq);
            alarmService.updateAlarmUnreadCount(userSeq, unreadCount);
        }
    }
}
