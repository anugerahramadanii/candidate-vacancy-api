package com.feignz.candidate_vacancy_api.entity.criteria;

import com.feignz.candidate_vacancy_api.util.CriteriaConstants;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AgeCriteria extends Criteria {
    @NotNull
    @Min(0)
    private Integer minAge;

    @NotNull
    @Min(0)
    private Integer maxAge;

    public AgeCriteria(Integer minAge, Integer maxAge, Integer weight) {
        super(weight);
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    @Override
    public String getType() {
        return CriteriaConstants.AGE;
    }
}
