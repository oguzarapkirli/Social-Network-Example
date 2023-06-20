/*
 * Copyright Oğuz Arapkirli (c) 2023.
 */

package com.oguzarapkirli.socialnetworkexample.service;

import com.oguzarapkirli.socialnetworkexample.dto.CommentRequest;
import com.oguzarapkirli.socialnetworkexample.exception.ApiException;
import com.oguzarapkirli.socialnetworkexample.model.Comment;
import com.oguzarapkirli.socialnetworkexample.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;


    public void createComment(CommentRequest commentRequest, String name) {
        if (!postService.getPostById(commentRequest.postId()).isActive() || postService.getPostById(commentRequest.postId()).isDeleted()) {
            throw new ApiException("Post is not active", HttpStatus.BAD_REQUEST);
        }
        var comment = new Comment();
        comment.setCreator(userService.findByUsername(name));
        comment.setPost(postService.getPostById(commentRequest.postId()));
        comment.setContent(commentRequest.content());
        commentRepository.save(comment);
    }

    public List<Comment> getCommentsByPostId(UUID postId) {
        if (!postService.getPostById(postId).isActive() || postService.getPostById(postId).isDeleted()) {
            throw new ApiException("Post is not active", HttpStatus.BAD_REQUEST);
        }
        if (!commentRepository.findAllByPostId(postId).isPresent()) {
            throw new ApiException("Comments not found", HttpStatus.BAD_REQUEST);
        }
        return commentRepository.findAllByPostId(postId).get();
    }

    public void deleteCommentById(UUID commentId, String name) {
        if (!commentRepository.existsById(commentId)) {
            throw new ApiException("Comment not found", HttpStatus.BAD_REQUEST);
        }
        if (!commentRepository.findById(commentId).get().getCreator().getUsername().equals(name)) {
            throw new ApiException("Comment is not created by user", HttpStatus.BAD_REQUEST);
        }

        var comment = commentRepository.findById(commentId).get();
        comment.setDeleted(true);
        comment.setActive(false);
        commentRepository.save(comment);
    }
}
