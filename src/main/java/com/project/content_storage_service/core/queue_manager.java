package com.project.content_storage_service.core;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class queue_manager {
    private final BlockingQueue<MultipartFile> file_queue = new ArrayBlockingQueue<>(1000);

    public boolean add_file_to_queue(MultipartFile file) {
         return file_queue.offer(file);
    }

    public MultipartFile pickup_file_for_upload() {

        return file_queue.poll();
    }

}