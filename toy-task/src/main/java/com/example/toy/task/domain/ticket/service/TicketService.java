package com.example.toy.task.domain.ticket.service;

import com.example.toy.common.event.ticket.TicketAcceptedEvent;
import com.example.toy.common.event.ticket.TicketCreatedEvent;
import com.example.toy.common.exception.CustomException;
import com.example.toy.common.exception.ExceptionCode;
import com.example.toy.core.task.dao.TaskRepository;
import com.example.toy.core.task.entity.Task;
import com.example.toy.core.task.entity.TaskStatus;
import com.example.toy.core.ticket.dao.TicketRepository;
import com.example.toy.core.ticket.entity.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TicketService {
    private final ApplicationEventPublisher eventPublisher;
    private final TicketRepository ticketRepository;
    private final TaskRepository taskRepository;

    @Transactional
    public void acceptUsingXLock(Long seq) {
        Ticket ticket = ticketRepository.findById(seq)
                .orElseThrow(() -> new CustomException(ExceptionCode.TICKET_NOT_FOUND));

        Long taskSeq = ticket.getTask().getSeq();

        // pessimistic lock
        Task task = taskRepository.findBySeq(taskSeq)
                .orElseThrow(() -> new CustomException(ExceptionCode.TASK_NOT_FOUND));
        if (!TaskStatus.MATCHING.equals(task.getStatus())) {
            throw new CustomException(ExceptionCode.TASK_IS_NOT_MATCHING);
        }

        long matchedCount = task.getMatchedCount();
        long requiredCount = task.getRequiredCount();

        task.increaseMatchedCount();

        if (matchedCount + 1 == requiredCount) {
            task.updateStatus(TaskStatus.COMPLETED);
        }

        eventPublisher.publishEvent(TicketAcceptedEvent
                .builder()
                .ticketSeq(seq)
                .build()
        );
    }

    @Transactional
    public void saveTicketsOf(Long taskSeq, List<Long> userSeqs) {
        Task task = taskRepository.findById(taskSeq)
                .orElseThrow(() -> new CustomException(ExceptionCode.TASK_NOT_FOUND));
        List<Ticket> tickets = userSeqs.stream().map(s -> Ticket.builder()
                        .proposedUserSeq(s)
                        .task(task)
                        .build())
                .toList();

        ticketRepository.saveAll(tickets);

        eventPublisher.publishEvent(
                TicketCreatedEvent.builder()
                        .userSeqs(userSeqs)
                        .taskSeq(taskSeq)
                        .build()
        );
    }
}
