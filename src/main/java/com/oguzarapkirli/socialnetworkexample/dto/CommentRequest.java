/*
 * Copyright Oğuz Arapkirli (c) 2023.
 */

package com.oguzarapkirli.socialnetworkexample.dto;

import java.util.UUID;

public record CommentRequest(
        String content,
        UUID postId
) {
}
