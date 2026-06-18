package com.example.Project_With_AI.notification.controller;

import com.example.Project_With_AI.notification.enums.NotificationType;
import com.example.Project_With_AI.notification.services.NotificationContextService;
import com.example.Project_With_AI.notification.services.OsThreadService;
import lombok.RequiredArgsConstructor;
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

    @Transactional
    @PostMapping("/send")
    ResponseEntity<String> sendNotification(
            @RequestParam NotificationType notificationType,
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam String message
            ) {
        System.out.println("Running Thread in controller = "+ Thread.currentThread());
        osThreadService.osThreadCall();
        notificationContextService.send(notificationType, source, destination, message);
        return ResponseEntity.ok("Notification sent successfully");
    }
}
