package com.project.content_storage_service.repo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class bucket_entity {

    @Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private String bucketName;
    private String fileName;
    private String filePath;


    public bucket_entity() {
    }

    public bucket_entity(String bucketName, String fileName, String filePath) {
        this.bucketName = bucketName;
        this.fileName = fileName;
        this.filePath = filePath;
    }
    
    public Long getId() {
        return id;
    }
    public String getBucketName() {
        return bucketName;
    }
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
