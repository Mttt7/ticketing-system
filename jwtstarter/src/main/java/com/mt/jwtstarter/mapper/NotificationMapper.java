package com.mt.jwtstarter.mapper;

import com.mt.jwtstarter.dto.Notification.NotificationResponseDto;
import com.mt.jwtstarter.model.Notification;
import org.springframework.stereotype.Service;

@Service
public class NotificationMapper {

    public static NotificationResponseDto mapToNotificationResponseDto(Notification notification){
        return NotificationResponseDto.builder()
                .id(notification.getId())
                .contentId(notification.getContentId())
                .type(notification.getType())
                .createdAt(notification.getCreatedAt())
                .isRead(notification.getIsRead())
                .build();
    }

}
