package com.example.toy.api.ticket.controller;

import com.example.toy.task.domain.ticket.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/tickets")
public class TicketController {
    private final TicketService ticketService;

    @PostMapping("{seq}/accept")
    public ResponseEntity<Void> accept(@PathVariable Long seq) {
        ticketService.accept(seq);
        return ResponseEntity.ok().build();
    }

}
