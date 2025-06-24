package com.project.content_storage_service.core;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.project.content_storage_service.concurrency.thread_manager;

@Component
public class file_system {

    private final queue_manager qm;
    private final thread_manager tm;

    public void upload_file_async(String dir_path, String file_name) {
        Path path = Path.of(dir_path);
        tm.submit_task(()->upload_file(path,file_name));
    }

    @Autowired
    public file_system(queue_manager qm, thread_manager tm) {
        this.qm = qm;
        this.tm = tm;
    }
    public void upload_file(Path targetDir, String file_name) {
        try {
            MultipartFile file = qm.pickup_file_for_upload();

            Files.createDirectories(targetDir);

            Path destination = targetDir.resolve(file_name);

            try (InputStream input = file.getInputStream()) {
                Files.copy(input, destination, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("✅ File saved to: " + destination);
            }


        } catch (Exception e) {
            e.printStackTrace();
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
