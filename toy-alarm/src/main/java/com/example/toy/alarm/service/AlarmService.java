package com.example.toy.alarm.service;

import com.example.toy.alarm.scheduler.AlarmScheduler;
import com.example.toy.core.alarm.dao.AlarmRepository;
import com.example.toy.core.alarm.dao.UserAlarmRepository;
import com.example.toy.core.alarm.entity.Alarm;
import com.example.toy.core.alarm.entity.UserAlarm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmRepository alarmRepository;
    private final UserAlarmRepository userAlarmRepository;

    @Transactional
    public void saveAlarm(Long userSeq, String message){
        Alarm alarm = Alarm.builder()
                .userSeq(userSeq)
                .message(message)
                .build();

        alarmRepository.save(alarm);
        AlarmScheduler.increaseAlarmUnreadCount(userSeq);
    }

    @Transactional
    public void updateAlarmUnreadCount(Long userSeq, Long unreadCount) {
        Optional<UserAlarm> userAlarmOptional = userAlarmRepository.findByUserSeq(userSeq);
        if (userAlarmOptional.isPresent()) {
            UserAlarm userAlarm = userAlarmOptional.get();
            userAlarm.increaseCount(unreadCount);
        }
    }
}
