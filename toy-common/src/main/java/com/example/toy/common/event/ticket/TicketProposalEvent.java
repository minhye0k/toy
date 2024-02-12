package com.example.toy.common.event.ticket;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class TicketProposalEvent {
    private Long taskSeq;
    private List<Long> userSeqs;
}
