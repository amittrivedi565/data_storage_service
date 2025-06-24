package com.univault.content_storage_service.bucket;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Component
public class file_validator {

    public record ValidationResult(boolean valid, String message) {}

    public  ValidationResult is_valid_pdf(MultipartFile file) {

        if(file.isEmpty()) {
            return new ValidationResult(false,"No file provided");
        }
        if (!"application/pdf".equals(file.getContentType())) {
            return new ValidationResult(false,"Only PDF files are allowed.");
        }

        String filename = file.getOriginalFilename();
        if (filename == null || !filename.toLowerCase().endsWith(".pdf")) {
            return new ValidationResult(false,"File with no name");
        }

        try (InputStream is = file.getInputStream()) {
            byte[] header = new byte[5];
            int bytesRead = is.read(header);
            if (bytesRead >= 4) {
                String headerString = new String(header, 0, 4);
                if ("%PDF".equals(headerString)) {
                    return new ValidationResult(true, "Valid PDF");
                } else {
                    return new ValidationResult(false, "File with invalid header");
                }
            }
        } catch (IOException e) {
            return new ValidationResult(false,"Error reading file: " + e.getMessage());
        }
        return new ValidationResult(false,"File validation error");
    }
}
