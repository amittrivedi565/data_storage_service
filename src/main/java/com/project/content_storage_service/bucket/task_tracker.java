package com.project.content_storage_service.bucket;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class task_tracker {
    private final Map<String, String> taskStatus = new HashMap<>();

    public void mark_started(String task_id){
        taskStatus.put(task_id,"IN PROGRESS");
    }
    public void mark_success(String task_id){
        taskStatus.put(task_id,"SUCCESSFULLY");
    }
    public void mark_failed(String task_id){
        taskStatus.put(task_id,"FAILED");
    }
    public String get_status(String taskId) {
        return taskStatus.getOrDefault(taskId, "UNKNOWN");
    }
}
