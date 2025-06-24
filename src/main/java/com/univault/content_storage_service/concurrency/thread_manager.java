package com.univault.content_storage_service.concurrency;

import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Component
public class thread_manager {

    private final ExecutorService executorService;

    public thread_manager() {
        this.executorService = new ThreadPoolExecutor(
                5, // core pool size
                10, // maximum pool size
                60L, // idle thread keep-alive time
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(500), // work queue
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy() // rejection policy
        );
    }

    public void submit_task(Runnable task) {
        executorService.submit(task);
    }

}
