package com.feignz.candidate_vacancy_api.exception;

import lombok.Getter;

@Getter
public class DuplicateDataException extends RuntimeException {
    private final String field;
    private final String code;

    public DuplicateDataException(String message) {
        super(message);
        this.field = null;
        this.code = null;
    }

    public DuplicateDataException(String field, String message) {
        super(message);
        this.field = field;
        this.code = null;
    }

    public DuplicateDataException(String field, String message, String code) {
        super(message);
        this.field = field;
        this.code = code;
    }
}
