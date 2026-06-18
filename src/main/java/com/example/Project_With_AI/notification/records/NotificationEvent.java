package com.example.Project_With_AI.notification.records;

import com.example.Project_With_AI.notification.enums.NotificationType;

public record NotificationEvent(
        NotificationType notificationType,
        String source,
        String destination,
        String message
) {
}
