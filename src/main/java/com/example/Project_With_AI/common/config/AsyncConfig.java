package com.example.Project_With_AI.common.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


@Configuration
@EnableAsync
public class AsyncConfig {


    @Bean("virtualTaskExecutor")
    public Executor virtualTaskExecutor() {

        return Executors.newVirtualThreadPerTaskExecutor();
    }


    @Bean("cpuTaskExecutor")
    public Executor cpuTaskExecutor() {

        return Executors.newFixedThreadPool(
                Runtime.getRuntime()
                        .availableProcessors()
        );
    }
}
