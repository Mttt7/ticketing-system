package com.mt.jwtstarter.dto.Notification;

import com.mt.jwtstarter.enums.NotificationType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@Builder
public class NotificationResponseDto {
    private Long id;
    private NotificationType type;
    private Long contentId;
    private Boolean isRead;
    private String text;
    private Timestamp createdAt;
}
