package com.project.content_storage_service.bucket;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Component
public class file_validator {

    public boolean is_valid_pdf(MultipartFile file) {
        // 1. Check MIME type
        if (!"application/pdf".equals(file.getContentType())) {
            return false;
        }

        // 2. Check file extension
        String filename = file.getOriginalFilename();
        if (filename == null || !filename.toLowerCase().endsWith(".pdf")) {
            return false;
        }

        // 3. Check file signature (magic bytes)
        try (InputStream is = file.getInputStream()) {
            byte[] header = new byte[5];
            int bytesRead = is.read(header);

            if (bytesRead >= 4) {
                String headerString = new String(header, 0, 4);
                return headerString.equals("%PDF");
            }
        } catch (IOException e) {
            System.err.println("Error reading file header: " + e.getMessage());
        }

        return false;
    }
}
