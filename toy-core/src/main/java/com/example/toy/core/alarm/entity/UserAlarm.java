package com.example.toy.core.alarm.entity;

import com.example.toy.core.BaseTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserAlarm extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column
    private Long userSeq;

    @Column
    private long unreadCount;

    public void increaseCount(long count) {
        unreadCount += count;
    }

    public void decreaseCount() {
        unreadCount--;
    }
}
