package com.mt.jwtstarter.repository;

import com.mt.jwtstarter.model.Comment;
import com.mt.jwtstarter.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByTicketId(Long ticketId, Pageable pageable);

    @Query("SELECT COUNT(DISTINCT c.ticket) FROM Comment c WHERE c.author = :user AND c.createdAt >= :startOfDay")
    Long countDistinctTicketsCommentedToday(@Param("user") UserEntity user, @Param("startOfDay") Timestamp startOfDay);
}
