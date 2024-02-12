package com.example.toy.common.event.ticket;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TicketCreatedEvent {
    private List<Long> userSeqs;
    private Long taskSeq;
}
