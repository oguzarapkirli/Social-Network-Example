package com.oguzarapkirli.socialnetworkexample.controller;

import com.oguzarapkirli.socialnetworkexample.service.FileService;
import com.oguzarapkirli.socialnetworkexample.util.core.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/file")
public class FileController {
    private final FileService fileService;
    private final String[] imageExtensions = {".png", ".jpg", ".jpeg", ".gif", ".bmp", ".svg"};
    private final Integer maxImageSize = 1024 * 1024 * 5;


    @PostMapping(consumes = {"multipart/form-data"}, produces = {"application/json"})
    public ApiResponse uploadFiles(@RequestParam("file") MultipartFile file, Principal principal, HttpServletRequest request) {
        try {
            if (file.isEmpty()) {
                return ApiResponse.error(HttpStatus.BAD_REQUEST, "File cannot be empty");
            }
            if (file.getSize() > maxImageSize) {
                return ApiResponse.error(HttpStatus.BAD_REQUEST, "File size cannot be bigger than 5MB");
            }
            if (!Objects.requireNonNull(file.getContentType()).startsWith("image/")) {
                return ApiResponse.error(HttpStatus.BAD_REQUEST, "File type must be an image");
            }
            boolean isImage = false;
            for (String extension : imageExtensions) {
                if (Objects.requireNonNull(file.getOriginalFilename()).endsWith(extension)) {
                    isImage = true;
                    break;
                }
            }
            if (!isImage) {
                return ApiResponse.error(HttpStatus.BAD_REQUEST, "File type must be an image (png, jpg, jpeg, gif, bmp, svg)");
            }

            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String newName = principal.getName() + System.currentTimeMillis() + extension;

            //Get the base url of the server
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/api/v1/file/" + newName;
            fileService.save(file, newName);
            String message = "File uploaded successfully: " + url;
            return ApiResponse.ok(message, "Files are uploaded");
        } catch (Exception e) {
            return ApiResponse.error(HttpStatus.EXPECTATION_FAILED, "Error uploading files : " + e.getMessage());
        }
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> getFileByName(@PathVariable String fileName) throws IOException {
        Resource resource = fileService.getFileByName(fileName);
        return ResponseEntity.ok().header(
                        "Content-Type", Files.probeContentType(resource.getFile().toPath()))
                .body(resource);
    }
}