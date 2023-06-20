package com.oguzarapkirli.socialnetworkexample.service;

import com.oguzarapkirli.socialnetworkexample.exception.ApiException;
import com.oguzarapkirli.socialnetworkexample.model.Like;
import com.oguzarapkirli.socialnetworkexample.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostService postService;
    private final UserService userService;

    public void likePost(UUID postId, String username) {
        if (likeRepository.findByPostIdAndCreatorId(postId, userService.findByUsername(username).getId()).isPresent()) {
            throw new ApiException("Post is already liked by user", HttpStatus.BAD_REQUEST);
        }
        if (!postService.getPostById(postId).isActive() || postService.getPostById(postId).isDeleted()) {
            throw new ApiException("Post is not active", HttpStatus.BAD_REQUEST);
        }
        if (postService.getPostById(postId).getCreator().getUsername().equals(username)) {
            throw new ApiException("User cannot like own post", HttpStatus.BAD_REQUEST);
        }

        var post = postService.getPostById(postId);
        var user = userService.findByUsername(username);
        var like = new Like(post, user);
        likeRepository.save(like);
    }

    public void unlikePost(UUID postId, String username) {
        if (likeRepository.findByPostIdAndCreatorId(postId, userService.findByUsername(username).getId()).isEmpty()) {
            throw new ApiException("Post is not liked by user", HttpStatus.BAD_REQUEST);
        }
        if (!postService.getPostById(postId).isActive() || postService.getPostById(postId).isDeleted()) {
            throw new ApiException("Post is not active", HttpStatus.BAD_REQUEST);
        }
        var like = likeRepository.findByPostIdAndCreatorId(postId, userService.findByUsername(username).getId()).get();
        like.setDeleted(true);
        like.setActive(false);
        likeRepository.save(like);
    }


}
