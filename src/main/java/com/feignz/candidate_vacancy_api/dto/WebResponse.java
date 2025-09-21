package com.feignz.candidate_vacancy_api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebResponse<T> {
    private List<ErrorDetail> errors;
    private T data;
    private PagingResponse paging;

    @Data
    @Builder
    public static class ErrorDetail {
        private String field;
        private String message;
        private String code;

        public static ErrorDetail of(String message) {
            return ErrorDetail.builder().message(message).build();
        }

        public static ErrorDetail of(String field, String message) {
            return ErrorDetail.builder().field(field).message(message).build();
        }

        public static ErrorDetail of(String field, String message, String code) {
            return ErrorDetail.builder().field(field).message(message).code(code).build();
        }
    }
}
