package com.example.toy.task.domain.ticket.service;

import com.example.toy.common.event.ticket.TicketAcceptedEvent;
import com.example.toy.core.task.dao.TaskRepository;
import com.example.toy.core.task.entity.Task;
import com.example.toy.core.task.entity.TaskStatus;
import com.example.toy.core.ticket.dao.TicketRepository;
import com.example.toy.core.ticket.entity.Ticket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RecordApplicationEvents
public class TicketServiceTest {
    @Autowired
    TicketService ticketService;
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    ApplicationEvents events;

    @AfterEach
    void tearDown() {
        ticketRepository.deleteAll();
        taskRepository.deleteAll();
    }

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();
        Task task = taskRepository.save(
                Task.builder()
                        .seq(1L)
                        .userSeq(2L)
                        .status(TaskStatus.MATCHING)
                        .subject("subject")
                        .requiredCount(4)
                        .startAt(now.plusHours(2))
                        .endAt(now.plusHours(3))
                        .build()
        );

        List<Ticket> ticketList = new ArrayList<>();
        for (long i = 1; i < 6; i++) {
            ticketList.add(Ticket.builder()
                    .seq(i)
                    .task(task)
                    .proposedUserSeq(i)
                    .isAccepted(false)
                    .build());
        }

        ticketRepository.saveAll(ticketList);


    }

    @Test
    void 티켓_수락시_이벤트_발행() {
        //given
        Long ticketSeq = 1L;

        //when
        ticketService.acceptUsingXLock(ticketSeq);

        //then
        int count = (int) events.stream(TicketAcceptedEvent.class).count();
        assertEquals(1, count);
    }

    @Test
    void 매칭_인원_동시성_테스트() throws InterruptedException {
        //given
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CountDownLatch countDownLatch = new CountDownLatch(3);

        //when

        for (long i = 1; i < 4; i++) {
            long seq = i;
            executorService.execute(() -> {
                ticketService.acceptUsingXLock(seq);
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();

        //then
        Task task = taskRepository.findById(1L).get();
        long matchedCount = task.getMatchedCount();
        assertEquals(3, matchedCount);
    }

    @Test
    void 동시_매칭_모집_완료_테스트() throws InterruptedException {
        //given
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        CountDownLatch countDownLatch = new CountDownLatch(4);

        // when
        for (long i = 1; i <= 4; i++) {
            long seq = i;
            executorService.execute(() -> {
                ticketService.acceptUsingXLock(seq);
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();

        //then
        Task task = taskRepository.findById(1L).get();
        TaskStatus status = task.getStatus();
        assertEquals(TaskStatus.COMPLETED, status);
    }
}