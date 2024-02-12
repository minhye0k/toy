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
public class Alarm extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column
    private String message;

    @Column
    private Long userSeq;

    @Column
    private boolean isRead;

    public void read(){
        this.isRead = true;
    }
}
