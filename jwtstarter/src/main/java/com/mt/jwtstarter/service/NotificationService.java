package com.mt.jwtstarter.service;

import com.mt.jwtstarter.dto.Notification.NotificationResponseDto;
import com.mt.jwtstarter.enums.NotificationType;
import org.springframework.data.domain.Page;

public interface NotificationService {
    Page<NotificationResponseDto> getAllUserNotifications(int pagenumber, int pageSize, Boolean onlyUnread);

    Long countUnreadNotifications();

    void markAsRead(Long notificationId);

    void createNotification(Long contentId, NotificationType type, Long userId);
}
