/*
 * Copyright Oğuz Arapkirli (c) 2023.
 */

package com.oguzarapkirli.socialnetworkexample.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "Username cannot be blank")
        @Size(min = 3, max = 15, message = "Username must be between 3 and 15 characters")
        String username,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password must contain at least one lowercase letter, one uppercase letter and one digit")
        String password,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email must be valid")
        @Size(max = 50, message = "Email must be maximum 50 characters")
        String email,
        String firstName,
        String lastName
) {
    public RegisterRequest {
        if (username != null) username = username.toLowerCase();
    }
}