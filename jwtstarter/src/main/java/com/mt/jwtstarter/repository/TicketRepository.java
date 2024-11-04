package com.mt.jwtstarter.repository;

import com.mt.jwtstarter.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long>{

    Page<Ticket> findAllByCustomerId(Long customerId, Pageable pageable);
}
