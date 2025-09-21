package com.feignz.candidate_vacancy_api.dto.request.criteria;

import com.feignz.candidate_vacancy_api.entity.Gender;
import com.feignz.candidate_vacancy_api.entity.criteria.GenderCriteria;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenderCriteriaRequest {

    @NotNull(message = "Gender is required for gender criteria")
    private Gender gender;

    public GenderCriteria toEntity(Integer weight) {
        Integer effectiveWeight = (weight != null && weight >= 1) ? weight : 1;
        return new GenderCriteria(gender, effectiveWeight);
    }
}