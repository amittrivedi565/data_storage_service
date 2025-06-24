package com.project.content_storage_service.core;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.project.content_storage_service.concurrency.thread_manager;

@Component
public class file_system {

    private final queue_manager qm;
    private final thread_manager tm;

    public void upload_file_async() {
        tm.submit_task(this::upload_file);
    }

    @Autowired
    public file_system(queue_manager qm, thread_manager tm) {
        this.qm = qm;
        this.tm = tm;
    }
    public void upload_file() {
        try {

            MultipartFile file = qm.pickup_file_for_upload();

            if(file == null) return ;

            Path uploadDir = Paths.get("uploads");

            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            try (InputStream input = file.getInputStream()) {
                Files.copy(input, uploadDir.resolve(file.getOriginalFilename()));
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }

    public byte[] download_file(String path) {
        try {
            if (path == null || path.isEmpty()) {
                throw new IllegalArgumentException("File path cannot be null or empty");
            }

            Path filePath = Paths.get(path);

            if (!Files.exists(filePath)) {
                throw new RuntimeException("File not found: " + path);
            }

            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to open file for download", e);
        }
    }

    public String delete_file(String path) {
        try {
            Path filePath = Paths.get(path);

            if (!Files.exists(filePath)) {
                throw new RuntimeException("File not found: " + path);
            }

            Files.delete(filePath);

            return ("File deleted successfully: " + path);

        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file", e);
        }
    }
}
