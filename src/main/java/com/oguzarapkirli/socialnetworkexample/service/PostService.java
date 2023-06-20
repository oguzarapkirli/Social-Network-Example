package com.oguzarapkirli.socialnetworkexample.service;

import com.oguzarapkirli.socialnetworkexample.dto.PostRequest;
import com.oguzarapkirli.socialnetworkexample.exception.ApiException;
import com.oguzarapkirli.socialnetworkexample.model.Post;
import com.oguzarapkirli.socialnetworkexample.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    public Post getPostById(UUID id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ApiException("Posts not found", HttpStatus.NOT_FOUND));
    }

    public void deletePostById(UUID id, String username) {
        if (!postRepository.existsById(id)) {
            throw new ApiException("Post not found", HttpStatus.NOT_FOUND);
        }
        if (!postRepository.findById(id).get().getCreator().getUsername().equals(username)) {
            throw new ApiException("Post is not created by user", HttpStatus.BAD_REQUEST);
        }
        final Post post = postRepository.findById(id).get();
        post.setDeleted(true);
        post.setActive(false);
        postRepository.save(post);
    }

    public List<Post> getPostsByUsername(String username) {
        return postRepository.findAllByCreatorId(userService.findByUsername(username).getId())
                .orElseThrow(() -> new ApiException("Posts not found", HttpStatus.NOT_FOUND));
    }

    public void createPost(PostRequest postRequest, String name) {
        var user = userService.findByUsername(name);
        var post = new Post();
        post.setCreator(user);
        post.setContent(postRequest.content());
        post.setDescription(postRequest.description());
        post.setType(postRequest.type());
        postRepository.save(post);
    }
}
