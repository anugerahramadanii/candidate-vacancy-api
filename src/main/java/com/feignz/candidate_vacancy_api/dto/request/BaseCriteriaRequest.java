package com.feignz.candidate_vacancy_api.dto.request;

import com.feignz.candidate_vacancy_api.dto.request.criteria.AgeCriteriaRequest;
import com.feignz.candidate_vacancy_api.dto.request.criteria.GenderCriteriaRequest;
import com.feignz.candidate_vacancy_api.dto.request.criteria.SalaryCriteriaRequest;
import com.feignz.candidate_vacancy_api.entity.criteria.Criteria;
import com.feignz.candidate_vacancy_api.util.CriteriaConstants;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseCriteriaRequest {

    @NotNull(message = "Type is required")
    @Pattern(regexp = "^(" + CriteriaConstants.AGE + "|" + CriteriaConstants.GENDER + "|" + CriteriaConstants.SALARY
            + ")$", message = "Type must be one of: " + CriteriaConstants.AGE + ", " + CriteriaConstants.GENDER + ", "
                    + CriteriaConstants.SALARY)
    private String type;

    @Min(value = 1, message = "Weight must be at least 1")
    @Builder.Default
    private Integer weight = 1;

    private AgeCriteriaRequest ageCriteria;
    private GenderCriteriaRequest genderCriteria;
    private SalaryCriteriaRequest salaryCriteria;

    public Criteria toEntity() {
        return switch (type.toUpperCase()) {
            case CriteriaConstants.AGE -> {
                if (ageCriteria == null) {
                    throw new IllegalArgumentException("Age criteria data is required for AGE type");
                }
                yield ageCriteria.toEntity(weight);
            }
            case CriteriaConstants.GENDER -> {
                if (genderCriteria == null) {
                    throw new IllegalArgumentException("Gender criteria data is required for GENDER type");
                }
                yield genderCriteria.toEntity(weight);
            }
            case CriteriaConstants.SALARY -> {
                if (salaryCriteria == null) {
                    throw new IllegalArgumentException("Salary criteria data is required for SALARY type");
                }
                yield salaryCriteria.toEntity(weight);
            }
            default -> throw new IllegalArgumentException("Unsupported criteria type: " + type);
        };
    }
}