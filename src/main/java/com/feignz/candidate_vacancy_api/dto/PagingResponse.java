package com.feignz.candidate_vacancy_api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PagingResponse {
    private Integer currentPage;
    private Integer totalPage;
    private Integer size;
    private Long totalElements;
}
