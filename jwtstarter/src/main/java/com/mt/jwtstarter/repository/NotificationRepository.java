package com.mt.jwtstarter.repository;

import com.mt.jwtstarter.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
