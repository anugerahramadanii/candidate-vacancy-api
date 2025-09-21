package com.feignz.candidate_vacancy_api.dto.request.criteria;

import com.feignz.candidate_vacancy_api.entity.criteria.AgeCriteria;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgeCriteriaRequest {

    @NotNull(message = "Minimum age is required for age criteria")
    @Min(value = 0, message = "Minimum age must be non-negative")
    @Max(value = 120, message = "Maximum age must not exceed 120")
    private Integer minAge;

    @NotNull(message = "Maximum age is required for age criteria")
    @Min(value = 0, message = "Maximum age must be non-negative")
    @Max(value = 120, message = "Maximum age must not exceed 120")
    private Integer maxAge;

    @AssertTrue(message = "Minimum age must be less than or equal to maximum age")
    public boolean isValidAgeRange() {
        if (minAge == null || maxAge == null)
            return true;

        return minAge <= maxAge;
    }

    public AgeCriteria toEntity(Integer weight) {
        Integer effectiveWeight = (weight != null && weight >= 1) ? weight : 1;
        return new AgeCriteria(minAge, maxAge, effectiveWeight);
    }
}