package com.oguzarapkirli.socialnetworkexample.controller;

import com.oguzarapkirli.socialnetworkexample.service.FileService;
import com.oguzarapkirli.socialnetworkexample.util.core.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/file")
public class FileController {
    private final FileService fileService;

    @PostMapping(consumes = {"multipart/form-data"}, produces = {"application/json"})
    public ApiResponse uploadFiles(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal Authentication principal) {
        String message = null;
        try {
            if (file.isEmpty()) {
                return ApiResponse.error(HttpStatus.BAD_REQUEST, "Please select a file to upload");
            }

            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String newName = principal.getName() + System.currentTimeMillis() + extension;
            fileService.save(file, newName);
            message = "File(s) uploaded successfully " + newName;
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