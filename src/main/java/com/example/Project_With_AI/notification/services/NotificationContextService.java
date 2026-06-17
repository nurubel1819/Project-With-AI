package com.example.Project_With_AI.notification.services;

import com.example.Project_With_AI.notification.enums.NotificationType;
import com.example.Project_With_AI.notification.interfaces.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationContextService {

    private final Map<String, Notification> strategies;

    @Async("virtualTaskExecutor")
    public void send(NotificationType type, String source, String destination, String message) {
        Notification strategy = strategies.get(type.name());
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported notification type: " + type);
        }
        strategy.sendNotification(source, destination, message);
    }
}