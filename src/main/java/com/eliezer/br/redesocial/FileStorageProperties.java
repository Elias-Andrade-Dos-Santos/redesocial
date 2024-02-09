package com.eliezer.br.redesocial;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    private String UploadDir;

    public String getUploadDir() {
        return UploadDir;
    }

    public void setUploadDir(String uploadDir) {
        UploadDir = uploadDir;
    }

    
}
