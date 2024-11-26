package com.mt.jwtstarter.repository;

import com.mt.jwtstarter.model.UserEntity;
import com.mt.jwtstarter.model.UserNotification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserNotificationRepository extends JpaRepository<UserNotification, Long> {

    Page<UserNotification> findAllByUser(UserEntity user, Pageable pageable);

    @Query("SELECT un FROM UserNotification un WHERE un.user = :user AND un.notification.isRead = :isRead")
    Page<UserNotification> findAllByUserAndIsRead(
            @Param("user") UserEntity user,
            Pageable pageable,
            @Param("isRead") Boolean isRead
    );

    @Query("SELECT COUNT(un) FROM UserNotification un WHERE un.user = :user AND un.notification.isRead = :isRead")
    Long countByUserAndIsRead(@Param("user") UserEntity user, @Param("isRead") Boolean isRead);
}
