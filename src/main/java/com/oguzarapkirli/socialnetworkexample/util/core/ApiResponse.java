package com.oguzarapkirli.socialnetworkexample.util.core;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * ApiResponse class is a wrapper class for ResponseEntity.
 * It is used to return a response with a specific status code and body.
 */
public class ApiResponse extends ResponseEntity<Object> {
    public ApiResponse(ApiResponseModel body, HttpStatusCode status) {
        super(body, status);
    }

    public static ApiResponse ok(Object data, String message) {
        ApiResponseModel response = new ApiResponseModel();
        response.setStatusCode(200);
        response.setMessage(message != null ? message : "OK");
        response.setHasError(false);
        response.setData(data);
        return new ApiResponse(response, HttpStatus.OK);
    }

    public static ApiResponse badRequest( String message, List<ValidationError> validationErrors) {
        ApiResponseModel response = new ApiResponseModel();
        response.setStatusCode(400);
        response.setMessage(message != null ? message : "Bad Request");
        response.setHasError(true);
        response.setValidationErrors(validationErrors);
        return new ApiResponse(response, HttpStatus.BAD_REQUEST);
    }

    public static ApiResponse notFound(String message) {
        ApiResponseModel response = new ApiResponseModel();
        response.setStatusCode(404);
        response.setMessage(message != null ? message : "Not Found");
        response.setHasError(true);
        return new ApiResponse(response, HttpStatus.NOT_FOUND);
    }

    public static ApiResponse unauthorized(String message) {
        ApiResponseModel response = new ApiResponseModel();
        response.setStatusCode(401);
        response.setMessage(message != null ? message : "Unauthorized");
        response.setHasError(true);
        return new ApiResponse(response, HttpStatus.UNAUTHORIZED);
    }

    public static ApiResponse forbidden(String message) {
        ApiResponseModel response = new ApiResponseModel();
        response.setStatusCode(403);
        response.setMessage(message != null ? message : "Forbidden");
        response.setHasError(true);
        return new ApiResponse(response, HttpStatus.FORBIDDEN);
    }

    public static ApiResponse internalServerError(String message) {
        ApiResponseModel response = new ApiResponseModel();
        response.setStatusCode(500);
        response.setMessage(message != null ? message : "Internal Server Error");
        response.setHasError(true);
        return new ApiResponse(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ApiResponse conflict(String message) {
        ApiResponseModel response = new ApiResponseModel();
        response.setStatusCode(409);
        response.setMessage(message != null ? message : "Conflict");
        response.setHasError(true);
        return new ApiResponse(response, HttpStatus.CONFLICT);
    }

    public static ApiResponse created(Object data, String message) {
        ApiResponseModel response = new ApiResponseModel();
        response.setStatusCode(201);
        response.setMessage(message != null ? message : "Created");
        response.setHasError(false);
        response.setData(data);
        return new ApiResponse(response, HttpStatus.CREATED);
    }

    public static ApiResponse noContent(String message) {
        ApiResponseModel response = new ApiResponseModel();
        response.setStatusCode(204);
        response.setMessage(message != null ? message : "No Content");
        response.setHasError(false);
        return new ApiResponse(response, HttpStatus.NO_CONTENT);
    }

    public static ApiResponse unprocessableEntity(String message) {
        ApiResponseModel response = new ApiResponseModel();
        response.setStatusCode(422);
        response.setMessage(message != null ? message : "Unprocessable Entity");
        response.setHasError(true);
        return new ApiResponse(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public static ApiResponse tooManyRequests(String message) {
        ApiResponseModel response = new ApiResponseModel();
        response.setStatusCode(429);
        response.setMessage(message != null ? message : "Too Many Requests");
        response.setHasError(true);
        return new ApiResponse(response, HttpStatus.TOO_MANY_REQUESTS);
    }

    public static ApiResponse unsupportedMediaType(String message) {
        ApiResponseModel response = new ApiResponseModel();
        response.setStatusCode(415);
        response.setMessage(message != null ? message : "Unsupported Media Type");
        response.setHasError(true);
        return new ApiResponse(response, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    public static ApiResponse error(HttpStatus status, String message) {
        ApiResponseModel response = new ApiResponseModel();
        response.setStatusCode(status.value());
        response.setMessage(message != null ? message : "Error");
        response.setHasError(true);
        return new ApiResponse(response, status);
    }

}
