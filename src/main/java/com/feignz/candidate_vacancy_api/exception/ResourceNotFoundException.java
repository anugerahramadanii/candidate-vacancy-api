package com.feignz.candidate_vacancy_api.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    private final String field;
    private final String code;

    public ResourceNotFoundException(String message) {
        super(message);
        this.field = null;
        this.code = null;
    }

    public ResourceNotFoundException(String field, String message) {
        super(message);
        this.field = field;
        this.code = null;
    }

    public ResourceNotFoundException(String field, String message, String code) {
        super(message);
        this.field = field;
        this.code = code;
    }
}
