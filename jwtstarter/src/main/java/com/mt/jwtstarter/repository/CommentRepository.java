package com.mt.jwtstarter.repository;

import com.mt.jwtstarter.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByTicketId(Long ticketId, Pageable pageable);
}
