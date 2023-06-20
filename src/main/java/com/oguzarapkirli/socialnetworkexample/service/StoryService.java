/*
 * Copyright Oğuz Arapkirli (c) 2023.
 */

package com.oguzarapkirli.socialnetworkexample.service;

import com.oguzarapkirli.socialnetworkexample.dto.StoryRequest;
import com.oguzarapkirli.socialnetworkexample.exception.ApiException;
import com.oguzarapkirli.socialnetworkexample.model.Story;
import com.oguzarapkirli.socialnetworkexample.repository.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoryService {
    private final StoryRepository storyRepository;
    private final UserService userService;

    public List<Story> getStoriesByUserID(UUID userID) {
        return storyRepository.findAllByCreatorId(userID)
                .orElseThrow(() -> new ApiException("Stories not found", HttpStatus.BAD_REQUEST));
    }

    public Story createStory(StoryRequest storyRequest, String username) {
        var story = new Story();
        story.setCreator(userService.findByUsername(username));
        story.setContent(storyRequest.content());
        story.setType(storyRequest.type());
        return storyRepository.save(story);
    }

    public Story deleteStory(UUID storyID, String username) {
        if (!storyRepository.existsById(storyID)) {
            throw new ApiException("Story not found", HttpStatus.BAD_REQUEST);
        }
        if (!storyRepository.findById(storyID).get().getCreator().getUsername().equals(username)) {
            throw new ApiException("Story is not yours", HttpStatus.BAD_REQUEST);
        }
        var story = storyRepository.findById(storyID).get();
        story.setDeleted(true);
        story.setActive(false);
        return storyRepository.save(story);
    }
}
