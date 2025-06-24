package com.project.content_storage_service.bucket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.project.content_storage_service.bucket.file_validator;

@RestController
@RequestMapping("/css")
public class bucket_controller {
    private final bucket_service bucketService;
    private final file_validator fileValid
            ;
    @Autowired
    public  bucket_controller(bucket_service bucketService, file_validator fileValid, path_generator pathGenerator){
        this.bucketService = bucketService;
        this.fileValid = fileValid;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,@RequestParam String path) {
        try {
            if (!fileValid.is_valid_pdf(file)) {
                System.err.println("Rejected non-PDF file: " + file.getOriginalFilename());
                return ResponseEntity.badRequest().body("Only PDF files are allowed.");
            }
            bucketService.upload_file_service(file,path);
            return ResponseEntity.ok("File queued and upload started.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Upload failed: " + e.getMessage());
        }
    }

}
