package com.example.Project_With_AI.notification.component;

import com.example.Project_With_AI.notification.records.NotificationEvent;
import com.example.Project_With_AI.notification.records.OsThreadEvent;
import com.example.Project_With_AI.notification.services.NotificationContextService;
import com.example.Project_With_AI.notification.services.OsThreadService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private static final Logger log = LoggerFactory.getLogger(NotificationEventListener.class);

    private final NotificationContextService notificationContextService;
    private final OsThreadService osThreadService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleNotificationAfterCommit(NotificationEvent event) {
        try {
            notificationContextService.send(
                    event.notificationType(),
                    event.source(),
                    event.destination(),
                    event.message()
            );
        } catch (Exception ex) {
            log.error("Notification dispatch failed. destination={}", event.destination(), ex);
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOsThreadAfterCommit(OsThreadEvent event) {
        try {
            osThreadService.osThreadCall();
        } catch (Exception ex) {
            log.error("OS thread call failed after commit", ex);
        }
    }
}
