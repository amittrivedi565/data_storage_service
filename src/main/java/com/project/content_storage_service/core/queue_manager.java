package com.project.content_storage_service.core;

import java.util.LinkedList;
import java.util.Queue;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class queue_manager {
    Queue<MultipartFile> file_queue = new LinkedList<>();

    public void add_file_to_queue(MultipartFile file) {
        file_queue.offer(file);
    }

    public MultipartFile pickup_file_for_upload() {
        return file_queue.poll();
    }

}