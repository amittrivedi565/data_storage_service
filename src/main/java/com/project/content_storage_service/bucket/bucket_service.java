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
    private final path_generator pg;

    @Autowired
    public bucket_service(queue_manager qm, file_system fs, path_generator pg) {
        this.qm = qm;
        this.fs = fs;
        this.pg = pg;
    }

    public void upload_file_service(MultipartFile file, String path) {
        if (file != null && !file.isEmpty()) {
            qm.add_file_to_queue(file);
            String full_path = pg.pg(path);
            String file_name = pg.generate_file_name(path);
            fs.upload_file_async(full_path,file_name);
        } else {
            throw new IllegalArgumentException("Cannot add null or empty file to queue.");
        }
    }
}
