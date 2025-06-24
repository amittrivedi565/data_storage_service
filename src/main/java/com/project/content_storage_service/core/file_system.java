package com.project.content_storage_service.core;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.project.content_storage_service.bucket.task_tracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.project.content_storage_service.concurrency.thread_manager;

@Component
public class file_system {

    private final queue_manager qm;
    private final thread_manager tm;
    private final task_tracker tt;

    @Autowired
    public file_system(queue_manager qm, thread_manager tm, task_tracker tt) {
        this.qm = qm;
        this.tm = tm;
        this.tt = tt;
    }

    public void upload_file_async(String dir_path, String file_name, String taskId) {
        Path path = Path.of(dir_path);
        tt.mark_started(taskId);

        tm.submit_task(() -> {
            try {
                upload_file(path, file_name);
                tt.mark_success(taskId);
            } catch (Exception e) {
                tt.mark_failed(taskId);
                System.err.println("Upload failed: " + e.getMessage());
            }
        });
    }

    public void upload_file(Path targetDir, String file_name) {
        try {
            MultipartFile file = qm.pickup_file_for_upload();

            Files.createDirectories(targetDir);

            Path destination = targetDir.resolve(file_name);

            try (InputStream input = file.getInputStream()) {
                Files.copy(input, destination, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File saved to: " + destination);
            }

        } catch (Exception e) {
            e.getMessage();
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
