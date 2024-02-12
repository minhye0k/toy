package com.example.toy.core.ticket.entity;

import com.example.toy.core.BaseTime;
import com.example.toy.core.task.entity.Task;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Ticket extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column
    private Long proposedUserSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_seq", referencedColumnName = "seq")
    private Task task;

    @Column
    private boolean isAccepted;

    public void accepted(){
        this.isAccepted = true;
    }
}
