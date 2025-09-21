package com.feignz.candidate_vacancy_api.entity.criteria;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import com.feignz.candidate_vacancy_api.util.CriteriaConstants;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SalaryRangeCriteria extends Criteria {
    @NotNull
    @Min(0)
    private BigDecimal minSalary;

    @NotNull
    @Min(0)
    private BigDecimal maxSalary;

    public SalaryRangeCriteria(BigDecimal minSalary, BigDecimal maxSalary, Integer weight) {
        super(weight);
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
    }

    @Override
    public String getType() {
        return CriteriaConstants.SALARY;
    }

}
