package com.mt.jwtstarter.repository;

import com.mt.jwtstarter.model.Ticket;
import com.mt.jwtstarter.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.sql.Timestamp;

public interface TicketRepository extends JpaRepository<Ticket, Long>, JpaSpecificationExecutor<Ticket> {

    Page<Ticket> findAllByCustomerId(Long customerId, Pageable pageable);

    Long countByCreatedAtAfter(Timestamp timestamp);

    Long countByClosedAtAfter(Timestamp timestamp);

    Long countByOpenedByAndCreatedAtAfter(UserEntity user, Timestamp timestamp);

    Long countByClosedByAndClosedAtAfter(UserEntity user, Timestamp timestamp);
}
