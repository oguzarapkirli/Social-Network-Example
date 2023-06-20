package com.oguzarapkirli.socialnetworkexample.dto;

import com.oguzarapkirli.socialnetworkexample.util.enums.StoryType;
public record StoryRequest(
        String content,
        StoryType type
) {
}
