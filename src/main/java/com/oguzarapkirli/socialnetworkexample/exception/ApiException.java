package com.oguzarapkirli.socialnetworkexample.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * ApiException class is a custom exception class for handling exceptions.
 * It is used to return a response with a specific status code and body.
 */
@Getter
public final class ApiException extends RuntimeException {
    private final String message;
    private final HttpStatus status;

    public ApiException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }


}
