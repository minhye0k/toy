package com.example.toy.common.event.ticket;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TicketAcceptedEvent {
    private Long ticketSeq;
}
