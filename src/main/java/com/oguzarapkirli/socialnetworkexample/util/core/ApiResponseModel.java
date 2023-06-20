package com.oguzarapkirli.socialnetworkexample.util.core;

import lombok.Data;

import java.util.List;

@Data
public class ApiResponseModel {
    private Integer statusCode;
    private String message;
    private boolean hasError;
    private Object data;
    private List<ValidationError> validationErrors;
}
