/*
 * Copyright Oğuz Arapkirli (c) 2023.
 */

package com.oguzarapkirli.socialnetworkexample.controller;

import com.oguzarapkirli.socialnetworkexample.dto.CommentRequest;
import com.oguzarapkirli.socialnetworkexample.service.CommentService;
import com.oguzarapkirli.socialnetworkexample.util.core.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ApiResponse createComment(@RequestBody @Valid CommentRequest commentRequest, Principal principal) {
        commentService.createComment(commentRequest, principal.getName());
        return ApiResponse.ok(null, "Comment is created");
    }

    @GetMapping("/{postId}")
    public ApiResponse getCommentsByPostId(@PathVariable UUID postId) {
        return ApiResponse.ok(commentService.getCommentsByPostId(postId), "Comments are retrieved");
    }

    @DeleteMapping("/{commentId}")
    public ApiResponse deleteCommentById(@PathVariable UUID commentId, Principal principal) {
        commentService.deleteCommentById(commentId, principal.getName());
        return ApiResponse.ok(null, "Comment is deleted");
    }


}
