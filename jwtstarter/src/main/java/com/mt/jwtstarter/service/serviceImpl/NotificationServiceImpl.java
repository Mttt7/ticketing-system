package com.mt.jwtstarter.service.serviceImpl;

import com.mt.jwtstarter.dto.Notification.NotificationResponseDto;
import com.mt.jwtstarter.enums.NotificationType;
import com.mt.jwtstarter.exception.EntityNotFound;
import com.mt.jwtstarter.mapper.NotificationMapper;
import com.mt.jwtstarter.model.Notification;
import com.mt.jwtstarter.model.UserEntity;
import com.mt.jwtstarter.model.UserNotification;
import com.mt.jwtstarter.repository.NotificationRepository;
import com.mt.jwtstarter.repository.UserNotificationRepository;
import com.mt.jwtstarter.repository.UserRepository;
import com.mt.jwtstarter.service.AuthService;
import com.mt.jwtstarter.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserNotificationRepository userNotificationRepository;
    private final AuthService authService;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public Page<NotificationResponseDto> getAllUserNotifications(int pageNumber, int pageSize, Boolean onlyUnread) {
        UserEntity loggedInUser = authService.getLoggedUser();
        Page<UserNotification> userNotifications;
        if (onlyUnread) {
            userNotifications = userNotificationRepository.findAllByUserAndIsRead(
                    loggedInUser,
                    PageRequest.of(pageNumber, pageSize),
                    false
            );
        } else {
            userNotifications = userNotificationRepository.findAllByUser(
                    loggedInUser,
                    PageRequest.of(pageNumber, pageSize, Sort.by("notification.createdAt").descending())
            );
        }

        Page<Notification> notifications = userNotifications.map(UserNotification::getNotification);
        return new PageImpl<>(
                notifications.getContent().stream().map(NotificationMapper::mapToNotificationResponseDto).toList(),
                PageRequest.of(pageNumber, pageSize),
                notifications.getTotalElements()
        );
    }

    @Override
    public Long countUnreadNotifications() {
        UserEntity loggedInUser = authService.getLoggedUser();
        return userNotificationRepository.countByUserAndIsRead(loggedInUser, false);
    }

    @Override
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }

    @Override
    public void createNotification(Long contentId, NotificationType type, Long userId) {
        Notification notification = new Notification();
        notification.setContentId(contentId);
        notification.setType(type);
        notification.setIsRead(false);
        notificationRepository.save(notification);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFound("User not found"));

        UserNotification userNotification = new UserNotification();
        userNotification.setUser(user);
        userNotification.setNotification(notification);
        userNotificationRepository.save(userNotification);
        messagingTemplate.convertAndSend("/topic/user/" + userId + "/notifications", notification);
    }
}
