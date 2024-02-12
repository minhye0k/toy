package com.example.toy.task.domain.ticket.event;

import com.example.toy.common.annotation.EventAsync;
import com.example.toy.common.event.ticket.TicketProposalEvent;
import com.example.toy.task.domain.ticket.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class TicketEventListener {
    private final TicketService ticketService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @EventAsync
    @EventListener
    public void ticketProposalEventListener(TicketProposalEvent ticketProposalEvent) {
        ticketService.saveTicketsOf(ticketProposalEvent.getTaskSeq(), ticketProposalEvent.getUserSeqs());
    }
}
