package com.feignz.candidate_vacancy_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.feignz.candidate_vacancy_api.entity.criteria.Criteria;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VacancyResponse {
    private String id;
    private String name;
    private List<Criteria> criteria;
}
