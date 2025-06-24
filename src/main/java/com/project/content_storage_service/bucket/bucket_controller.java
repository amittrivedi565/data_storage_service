package com.project.content_storage_service.bucket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/css")
public class bucket_controller {
    private final bucket_service bucketService;
    private final file_validator fileValid;
    private final task_tracker tasktracker;
    @Autowired
    public  bucket_controller(bucket_service bucketService, file_validator fileValid, task_tracker tasktracker){
        this.bucketService = bucketService;
        this.fileValid = fileValid;
        this.tasktracker = tasktracker;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("path") String path) {
        try {
            file_validator.ValidationResult result = fileValid.is_valid_pdf(file);

            if (!result.valid()) {
                System.err.println("Rejected file: " + result.message());
                return ResponseEntity.badRequest().body(result.message());
            }

            String taskId = bucketService.upload_file_service(file, path);
            return ResponseEntity.ok("File queued and upload started."+taskId);
        } catch (Exception e) {
            System.err.println("Upload failed: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/status/{taskId}")
    public ResponseEntity<String> checkUploadStatus(@PathVariable String taskId) {
        String status = tasktracker.get_status(taskId);
        return ResponseEntity.ok("Upload status: " + status);
    }

}
