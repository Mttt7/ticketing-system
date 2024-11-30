package com.mt.jwtstarter.repository;

import com.mt.jwtstarter.model.Subcategory;
import com.mt.jwtstarter.model.Ticket;
import com.mt.jwtstarter.model.UserEntity;
import com.mt.jwtstarter.specification.TicketSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.sql.Timestamp;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long>, JpaSpecificationExecutor<Ticket> {

    Page<Ticket> findAllByCustomerId(Long customerId, Pageable pageable);

    Long countByCreatedAtAfter(Timestamp timestamp);

    Long countByClosedAtAfter(Timestamp timestamp);

    Long countByOpenedByAndCreatedAtAfter(UserEntity user, Timestamp timestamp);

    Long countByClosedByAndClosedAtAfter(UserEntity user, Timestamp timestamp);

//    Page<Ticket> findAll(Specification<Ticket> spec, Pageable pageable);

}
