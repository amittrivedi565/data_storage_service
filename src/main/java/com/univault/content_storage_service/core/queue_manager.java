package com.univault.content_storage_service.core;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class queue_manager {
    private final BlockingQueue<MultipartFile> queue = new ArrayBlockingQueue<>(1000);

    public void add_file_to_queue(MultipartFile file) {
        System.out.println("File queued: " + file.getOriginalFilename());
        queue.offer(file);
    }

    public MultipartFile pickup_file_for_upload() {
        MultipartFile file = queue.poll();
        if (file != null) {
            System.out.println("Picked file: " + file.getOriginalFilename());
        }
        return file;
    }


}