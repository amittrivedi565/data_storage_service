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

    public String generate_file_name(String params) {
        // rgpv/btech/cse/1/dsa/unit1
        String[] parts = params.split("/");

        String uni = parts[0].toUpperCase();
        String course = parts[1].toUpperCase();
        String branch = parts[2].toUpperCase();
        String sem = parts[3];
        String subject = parts[4].toUpperCase();
        String unit = parts[5].toLowerCase().replace("unit", "Unit-");

        return unit + "_" + subject + "_" + branch + "_" +sem + "_Sem" + "_" + course + "_" + uni + " -" + " www.univault.in";
    }
}
