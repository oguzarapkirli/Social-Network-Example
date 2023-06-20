package com.oguzarapkirli.socialnetworkexample.service;

import com.oguzarapkirli.socialnetworkexample.exception.ApiException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {
    private final Path rootDir = Paths.get("uploads");

    FileService() {
        init();
    }

    public void init() {
        try {
            File tempDir = new File(rootDir.toUri());
            boolean dirExists = tempDir.exists();
            if (!dirExists) {
                Files.createDirectory(rootDir);
            }
        } catch (IOException e) {
            throw new ApiException("Error creating directory", HttpStatus.BAD_REQUEST);
        }
    }

    public void save(MultipartFile file, String name) {
        try {
            Files.copy(file.getInputStream(),
                    this.rootDir.resolve(name));
        } catch (Exception e) {
            throw new ApiException("Error uploading files with: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public Resource getFileByName(String fileName) {
        try {
            Path filePath = rootDir.resolve(fileName);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new ApiException("Could not read file", HttpStatus.BAD_REQUEST);
            }
        } catch (MalformedURLException mal) {
            throw new ApiException("Error: " + mal.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
