package com.example.Project_With_AI.notification.services;

import com.example.Project_With_AI.notification.interfaces.Notification;
import org.springframework.stereotype.Service;

@Service("SMS")
public class SmsNotificationService implements Notification {
    @Override
    public void sendNotification(String source, String destination, String message) {
        System.out.println("Running Thread inside SMS = "+ Thread.currentThread());
        System.out.println("SMS Notification: "+ " source = "+source+" destination = "+destination+" message = " + message);
    }
}
