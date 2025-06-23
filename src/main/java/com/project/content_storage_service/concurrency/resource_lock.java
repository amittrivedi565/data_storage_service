
package com.project.content_storage_service.concurrency;

import org.springframework.stereotype.Component;

@Component
public class resource_lock {
    private boolean isLocked = false;

    public synchronized  void lock() throws InterruptedException {
        while (isLocked) {
            wait();
        }
        isLocked = true;
    }

    public synchronized void unlock() {
        isLocked = false;
        notifyAll();
    }

    public synchronized boolean isLocked() {
        return isLocked;
    }
}
