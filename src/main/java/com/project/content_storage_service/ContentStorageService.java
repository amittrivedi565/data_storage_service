package com.project.content_storage_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.project.content_storage_service.services.bucket_service;

@SpringBootApplication
public class ContentStorageService {
	public static void main(String[] args) {
		SpringApplication.run(ContentStorageService.class, args);
	}

}
