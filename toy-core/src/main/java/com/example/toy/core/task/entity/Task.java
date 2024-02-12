package com.example.toy.core.task.entity;

import com.example.toy.core.BaseTime;
import com.example.toy.core.ticket.entity.Ticket;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Task extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column
    private Long userSeq;

    @Column
    private long matchedCount;

    @Column
    private long requiredCount;

    @Column
    private String subject;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column
    private LocalDateTime startAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column
    private LocalDateTime endAt;

    @Column
    @Enumerated(value = EnumType.STRING)
    private TaskStatus status;

    @OneToMany(mappedBy = "task")
    private List<Ticket> tickets = new ArrayList<>();

    public void updateStatus(TaskStatus taskStatus){
        this.status = taskStatus;
    }

    public void increaseMatchedCount(){
        this.matchedCount++;
    }
}
