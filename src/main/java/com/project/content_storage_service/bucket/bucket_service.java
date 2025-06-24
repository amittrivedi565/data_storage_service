package com.project.content_storage_service.bucket;

import com.project.content_storage_service.core.file_system;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.content_storage_service.core.queue_manager;

@Service
public class bucket_service {

    private final queue_manager qm;
    private final file_system fs;

    @Autowired
    public bucket_service(queue_manager qm, file_system fs) {
        this.qm = qm;
        this.fs = fs;
    }

    public void upload_file_service(MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            qm.add_file_to_queue(file);
            fs.upload_file_async();
        } else {
            throw new IllegalArgumentException("Cannot add null or empty file to queue.");
        }
    }
}
