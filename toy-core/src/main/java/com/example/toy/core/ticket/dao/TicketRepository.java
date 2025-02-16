package com.example.toy.core.ticket.dao;

import com.example.toy.core.ticket.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByTaskSeq(Long taskSeq);
}
