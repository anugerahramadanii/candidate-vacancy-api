package com.feignz.candidate_vacancy_api.entity.criteria;

import com.feignz.candidate_vacancy_api.entity.Gender;
import com.feignz.candidate_vacancy_api.util.CriteriaConstants;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GenderCriteria extends Criteria {
    @NotNull
    private Gender gender;

    public GenderCriteria(Gender gender, Integer weight) {
        super(weight);
        this.gender = gender;
    }

    @Override
    public String getType() {
        return CriteriaConstants.GENDER;
    }
}
