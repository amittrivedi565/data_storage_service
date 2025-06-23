package com.project.content_storage_service.concurrency;

import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class thread_manager {

    private static final int THREAD_POOL_SIZE = 10;
    
    private final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    public void submitTask(Runnable task) {
        executorService.submit(task);
    }
}
