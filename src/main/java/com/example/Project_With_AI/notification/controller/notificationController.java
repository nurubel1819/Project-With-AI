package com.example.Project_With_AI.notification.controller;

import com.example.Project_With_AI.notification.enums.NotificationType;
import com.example.Project_With_AI.notification.records.NotificationEvent;
import com.example.Project_With_AI.notification.records.OsThreadEvent;
import com.example.Project_With_AI.notification.services.NotificationContextService;
import com.example.Project_With_AI.notification.services.OsThreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class notificationController {

    private final NotificationContextService notificationContextService;
    private final OsThreadService osThreadService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    @PostMapping("/send")
    ResponseEntity<String> sendNotification(
            @RequestParam NotificationType notificationType,
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam String message
    ) {
        System.out.println("Running Thread in controller = " + Thread.currentThread());
        eventPublisher.publishEvent(new OsThreadEvent());
        eventPublisher.publishEvent(
                new NotificationEvent(notificationType, source, destination, message)
        );
        // if any exception occurs, the transaction will be rolled back and both thread not run
        return ResponseEntity.ok("Notification sent successfully");
    }
}
