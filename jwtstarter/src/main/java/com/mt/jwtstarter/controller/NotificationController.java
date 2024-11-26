package com.mt.jwtstarter.controller;

import com.mt.jwtstarter.dto.Notification.NotificationResponseDto;
import com.mt.jwtstarter.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
@CrossOrigin("*")
public class NotificationController {
    private final NotificationService notificationService;


    @GetMapping("/all")
    public ResponseEntity<Page<NotificationResponseDto>> getAllUserNotifications(int pageNumber,
                                                                                 int pageSize, Boolean onlyUnread) {
        return ResponseEntity.ok(notificationService.getAllUserNotifications(pageNumber, pageSize, onlyUnread));
    }

    @GetMapping("/count-unread")
    public ResponseEntity<Long> countUnreadNotifications() {
        return ResponseEntity.ok(notificationService.countUnreadNotifications());
    }

    @PostMapping("/mark-as-read/{notificationId}")
    public ResponseEntity<Void> markAsRead(@PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.ok().build();
    }
}
