package com.mt.jwtstarter.repository;

import com.mt.jwtstarter.model.Ticket;
import com.mt.jwtstarter.model.UserEntity;
import com.mt.jwtstarter.model.UserTicketFollower;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserTicketFollowerRepository extends JpaRepository<UserTicketFollower, Long> {

    Page<UserTicketFollower> findAllByUserAndTicket_IsOpen(UserEntity user, Boolean isOpen, Pageable pageable);

    UserTicketFollower findByUser(UserEntity user);

    Optional<UserTicketFollower> findByUserAndTicket(UserEntity user, Ticket ticket);

    List<UserTicketFollower> findAllByTicket(Ticket ticket);
}
