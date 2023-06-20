package com.oguzarapkirli.socialnetworkexample.util.core;

import lombok.Data;

@Data
public class ValidationError {
    private String field;
    private String message;
}
