package com.mt.jwtstarter.repository;

import com.mt.jwtstarter.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long>{
}
