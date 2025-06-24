package com.project.content_storage_service.bucket;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class path_generator {
    private static final String BUCKET_NAME = "uploads";

    public String pg(String path) {
        String normalizedPath = path.replace("/", File.separator);
        String fullPath = BUCKET_NAME + File.separator + normalizedPath;

        File dir = new File(fullPath);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new RuntimeException("Error creating directory: " + dir.getAbsolutePath());
        }

        return fullPath;
    }
}
