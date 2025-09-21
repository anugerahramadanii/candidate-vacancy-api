package com.feignz.candidate_vacancy_api.entity.criteria;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class Criteria {
    @NotNull
    @Min(1)
    private Integer weight;

    public Criteria(Integer weight) {
        this.weight = weight;
    }

    public abstract String getType();
}
