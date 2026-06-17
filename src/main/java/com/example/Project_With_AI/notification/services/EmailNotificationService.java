package com.example.Project_With_AI.notification.services;

import com.example.Project_With_AI.notification.interfaces.Notification;

public class EmailNotificationService implements Notification {
    @Override
    public void sendNotification(String source, String destination, String message) {
        System.out.println("Email Notification: "+ " source = "+source+" destination = "+destination+" message = " + message);

    }
}
