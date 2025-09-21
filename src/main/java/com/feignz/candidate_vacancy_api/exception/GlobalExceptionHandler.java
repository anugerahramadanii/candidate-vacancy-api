package com.feignz.candidate_vacancy_api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.feignz.candidate_vacancy_api.dto.WebResponse;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<WebResponse<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.warn("Validation error occurred: {}", ex.getMessage());

        List<WebResponse.ErrorDetail> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> WebResponse.ErrorDetail.of(
                        error.getField(),
                        error.getDefaultMessage(),
                        "VALIDATION_ERROR"))
                .toList();

        WebResponse<String> response = WebResponse.<String>builder()
                .errors(errors)
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<WebResponse<String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.warn("Resource not found: {}", ex.getMessage());

        WebResponse<String> response = WebResponse.<String>builder()
                .errors(List.of(WebResponse.ErrorDetail.of(
                        ex.getField(),
                        ex.getMessage(),
                        ex.getCode() != null ? ex.getCode() : "RESOURCE_NOT_FOUND")))
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(DuplicateDataException.class)
    public ResponseEntity<WebResponse<String>> handleDuplicateDataException(DuplicateDataException ex) {
        log.warn("Duplicate data error: {}", ex.getMessage());

        WebResponse<String> response = WebResponse.<String>builder()
                .errors(List.of(WebResponse.ErrorDetail.of(
                        ex.getField(),
                        ex.getMessage(),
                        ex.getCode() != null ? ex.getCode() : "DUPLICATE_DATA")))
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<WebResponse<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("Illegal argument error: {}", ex.getMessage());

        WebResponse<String> response = WebResponse.<String>builder()
                .errors(List.of(WebResponse.ErrorDetail.of(ex.getMessage())))
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<WebResponse<String>> handleGenericException(Exception ex) {
        log.error("Unexpected exception occurred: {}", ex.getMessage(), ex);

        WebResponse<String> response = WebResponse.<String>builder()
                .errors(List.of(WebResponse.ErrorDetail.of("An unexpected error occurred")))
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
