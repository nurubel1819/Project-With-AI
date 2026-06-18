package com.example.Project_With_AI.notification.services;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OsThreadService {
    @Async("cpuTaskExecutor")
    public void osThreadCall() {
        System.out.println("Running Thread inside OS thread service = "+ Thread.currentThread());
    }
}
