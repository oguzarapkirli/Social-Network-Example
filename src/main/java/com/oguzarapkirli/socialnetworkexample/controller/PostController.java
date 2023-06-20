package com.oguzarapkirli.socialnetworkexample.controller;

import com.oguzarapkirli.socialnetworkexample.dto.PostRequest;
import com.oguzarapkirli.socialnetworkexample.service.PostService;
import com.oguzarapkirli.socialnetworkexample.util.core.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ApiResponse createPost(@RequestBody @Valid PostRequest postRequest, @AuthenticationPrincipal Authentication principal) {
        postService.createPost(postRequest, principal.getName());
        return ApiResponse.ok(null, "Post is created");
    }

    @GetMapping("/{postId}")
    public ApiResponse getPostById(@PathVariable UUID postId) {
        return ApiResponse.ok(postService.getPostById(postId), "Post is retrieved");
    }

    @DeleteMapping("/{postId}")
    public ApiResponse deletePostById(@PathVariable UUID postId, @AuthenticationPrincipal Authentication principal) {
        postService.deletePostById(postId, principal.getName());
        return ApiResponse.ok(null, "Post is deleted");
    }

    @GetMapping("/{username}/posts")
    public ApiResponse getPostsByUsername(@PathVariable String username) {
        return ApiResponse.ok(postService.getPostsByUsername(username), "Posts are retrieved");
    }


}
