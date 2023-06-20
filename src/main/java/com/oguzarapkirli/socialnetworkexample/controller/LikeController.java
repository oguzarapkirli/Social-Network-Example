package com.oguzarapkirli.socialnetworkexample.controller;

import com.oguzarapkirli.socialnetworkexample.service.LikeService;
import com.oguzarapkirli.socialnetworkexample.util.core.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/{postId}/like")
    public ApiResponse like (@PathVariable UUID postId, @AuthenticationPrincipal Authentication principal) {
        likeService.likePost(postId, principal.getName());
        return ApiResponse.ok( null, "Post is liked");
    }

    @PostMapping("/{postId}/unlike")
    public ApiResponse unlike (@PathVariable UUID postId, @AuthenticationPrincipal Authentication principal) {
        likeService.unlikePost(postId, principal.getName());
        return ApiResponse.ok( null, "Post is unliked");
    }


}
