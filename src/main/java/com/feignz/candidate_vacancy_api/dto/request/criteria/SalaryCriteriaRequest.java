package com.feignz.candidate_vacancy_api.dto.request.criteria;

import com.feignz.candidate_vacancy_api.entity.criteria.SalaryRangeCriteria;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaryCriteriaRequest {

    @NotNull(message = "Minimum salary is required for salary criteria")
    @DecimalMin(value = "0.0", inclusive = false, message = "Minimum salary must be positive")
    @DecimalMax(value = "999999999.99", message = "Maximum salary must not exceed 999,999,999.99")
    private BigDecimal minSalary;

    @NotNull(message = "Maximum salary is required for salary criteria")
    @DecimalMin(value = "0.0", inclusive = false, message = "Maximum salary must be positive")
    @DecimalMax(value = "999999999.99", message = "Maximum salary must not exceed 999,999,999.99")
    private BigDecimal maxSalary;

    @AssertTrue(message = "Minimum salary must be less than or equal to maximum salary")
    public boolean isValidSalaryRange() {
        if (minSalary == null || maxSalary == null)
            return true;

        return minSalary.compareTo(maxSalary) <= 0;
    }

    public SalaryRangeCriteria toEntity(Integer weight) {
        Integer effectiveWeight = (weight != null && weight >= 1) ? weight : 1;
        return new SalaryRangeCriteria(minSalary, maxSalary, effectiveWeight);
    }
}