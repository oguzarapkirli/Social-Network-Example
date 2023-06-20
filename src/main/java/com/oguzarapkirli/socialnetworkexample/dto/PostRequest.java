package com.oguzarapkirli.socialnetworkexample.dto;

import com.oguzarapkirli.socialnetworkexample.util.enums.PostType;

public record PostRequest(
        String content,
        String description,
        PostType type
) {
    public PostRequest {
        if (content != null && type == PostType.TEXT) {
            description = null;
        }
    }
}
