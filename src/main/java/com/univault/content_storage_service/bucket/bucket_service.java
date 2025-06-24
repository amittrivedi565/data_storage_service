package com.univault.content_storage_service.bucket;

import com.univault.content_storage_service.core.file_system;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.univault.content_storage_service.core.queue_manager;

import java.util.UUID;

@Service
public class bucket_service {

    private final queue_manager qm;
    private final file_system fs;
    private final path_generator pg;
    private final task_tracker tt;

    @Autowired
    public bucket_service(queue_manager qm, file_system fs, path_generator pg, task_tracker tt) {
        this.qm = qm;
        this.fs = fs;
        this.pg = pg;
        this.tt = tt;
    }

    public String upload_file_service(MultipartFile file, String path) {
       try{
           qm.add_file_to_queue(file);

           String full_path = pg.pg(path);
           String file_name = pg.generate_file_name(path);
           String task_id = UUID.randomUUID().toString();

           fs.upload_file_async(full_path,file_name,task_id);
           return task_id;

       } catch (RuntimeException e) {
           throw new RuntimeException(e.getMessage());
       }
    }
}
