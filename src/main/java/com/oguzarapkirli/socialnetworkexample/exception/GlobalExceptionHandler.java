package com.oguzarapkirli.socialnetworkexample.exception;

import com.oguzarapkirli.socialnetworkexample.util.core.ValidationError;
import com.oguzarapkirli.socialnetworkexample.util.core.ApiResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse handleValidationErrors(MethodArgumentNotValidException ex) {
        List<ValidationError> validationErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> {
                    ValidationError validationError = new ValidationError();
                    validationError.setField(fieldError.getField());
                    validationError.setMessage(fieldError.getDefaultMessage());
                    return validationError;
                })
                .toList();

        return ApiResponse.badRequest("Validation error(s) occurred. Please check the fields.", validationErrors);
    }

    @ExceptionHandler(ApiException.class)
    public ApiResponse handleException(ApiException ex) {
        return switch (ex.getStatus()) {
            case BAD_REQUEST -> ApiResponse.badRequest(ex.getMessage(),null);
            case UNAUTHORIZED -> ApiResponse.unauthorized(ex.getMessage());
            case FORBIDDEN -> ApiResponse.forbidden(ex.getMessage());
            case NOT_FOUND -> ApiResponse.notFound(ex.getMessage());
            case TOO_MANY_REQUESTS -> ApiResponse.tooManyRequests(ex.getMessage());
            case CONFLICT -> ApiResponse.conflict(ex.getMessage());
            case UNSUPPORTED_MEDIA_TYPE -> ApiResponse.unsupportedMediaType(ex.getMessage());
            case INTERNAL_SERVER_ERROR -> ApiResponse.internalServerError(ex.getMessage());
            default -> ApiResponse.internalServerError(ex.getMessage());
        };
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse handleException(Exception ex) {
        return ApiResponse.internalServerError(ex.getMessage());
    }

}
