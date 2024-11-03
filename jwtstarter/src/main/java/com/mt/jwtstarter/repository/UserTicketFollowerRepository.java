package com.mt.jwtstarter.repository;

import com.mt.jwtstarter.model.UserEntity;
import com.mt.jwtstarter.model.UserTicketFollower;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTicketFollowerRepository extends JpaRepository<UserTicketFollower, Long> {

    Page<UserTicketFollower> findAllByUser(UserEntity user, Pageable pageable);

    UserTicketFollower findByUser(UserEntity user);
}
