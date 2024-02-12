package com.example.toy.core.alarm.dao;

import com.example.toy.core.alarm.entity.UserAlarm;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface UserAlarmRepository extends JpaRepository<UserAlarm, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<UserAlarm> findByUserSeq(Long userSeq);
}
