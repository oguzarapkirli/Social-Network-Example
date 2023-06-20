package com.oguzarapkirli.socialnetworkexample.controller;

import com.oguzarapkirli.socialnetworkexample.dto.StoryRequest;
import com.oguzarapkirli.socialnetworkexample.service.StoryService;
import com.oguzarapkirli.socialnetworkexample.util.core.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/story")
@RequiredArgsConstructor
public class StoryController {
    private final StoryService storyService;

    @GetMapping("/{userID}")
    public ApiResponse getStoriesByUserID(@PathVariable UUID userID) {
        var stories = storyService.getStoriesByUserID(userID);
        return ApiResponse.ok(stories, "Stories are retrieved");
    }

    @PostMapping
    public ApiResponse createStory(@RequestBody @Valid StoryRequest storyRequest, @AuthenticationPrincipal Authentication authentication) {
        var story = storyService.createStory(storyRequest, authentication.getName());
        return ApiResponse.ok(story, "Story is created");
    }

    @DeleteMapping("/{storyID}")
    public ApiResponse deleteStory(@PathVariable UUID storyID, @AuthenticationPrincipal Authentication authentication) {
        var story = storyService.deleteStory(storyID, authentication.getName());
        return ApiResponse.ok(story, "Story is deleted");
    }
}

