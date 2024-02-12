package com.example.toy.alarm.event.listener;

import com.example.toy.alarm.service.AlarmService;
import com.example.toy.common.annotation.EventAsync;
import com.example.toy.common.event.matching.MatchingEvent;
import com.example.toy.common.event.ticket.TicketAcceptedEvent;
import com.example.toy.common.event.ticket.TicketCreatedEvent;
import com.example.toy.core.task.dao.TaskRepository;
import com.example.toy.core.task.entity.Task;
import com.example.toy.core.ticket.dao.TicketRepository;
import com.example.toy.core.ticket.entity.Ticket;
import com.example.toy.quartz.handler.QuartzHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AlarmEventListener {
    private final AlarmService alarmService;
    private final QuartzHandler quartzHandler;
    private final TaskRepository taskRepository;
    private final TicketRepository ticketRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @EventAsync
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void matchingEventListener(MatchingEvent matchingEvent) {
        Optional<Task> taskOptional = taskRepository.findById(matchingEvent.getTaskSeq());
        if (taskOptional.isPresent()) {
            String message = "매칭이 시작되었습니다.";

            Task task = taskOptional.get();
            alarmService.saveAlarm(task.getUserSeq(), message);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @EventAsync
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void ticketCreatedEventListener(TicketCreatedEvent ticketCreatedEvent) {
        Optional<Task> taskOptional = taskRepository.findById(ticketCreatedEvent.getTaskSeq());
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();

            String message = String.format("업무 제안을 받으셨습니다.\n제목 : %s", task.getSubject());

            List<Long> userSeqs = ticketCreatedEvent.getUserSeqs();
            userSeqs.forEach(u -> alarmService.saveAlarm(u, message));
        }
    }

    @EventAsync
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void ticketAcceptedEventListener(TicketAcceptedEvent ticketAcceptedEvent) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketAcceptedEvent.getTicketSeq());
        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();
            Task task = ticket.getTask();

            String message = "매칭이 완료 되었습니다.";

            alarmService.saveAlarm(ticket.getProposedUserSeq(), message);

            quartzHandler.addWorkStartReminderJob(ticket.getSeq(), task.getStartAt());
            quartzHandler.addWorkEndReminderJob(ticket.getSeq(), task.getEndAt());
        }
    }

}
