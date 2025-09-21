package com.feignz.candidate_vacancy_api.dto.request;

import com.feignz.candidate_vacancy_api.util.CriteriaConstants;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VacancyRequest {
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @NotEmpty(message = "At least one criterion is required")
    @Size(min = 1, max = 10, message = "Vacancy must have 1-10 criteria")
    @Valid
    private List<BaseCriteriaRequest> criteria;

    @AssertTrue(message = "Vacancy must have at least one required criteria type (" + CriteriaConstants.AGE + ", "
            + CriteriaConstants.GENDER + ", " + CriteriaConstants.SALARY + ")")
    public boolean hasRequiredCriteria() {
        if (criteria == null)
            return false;

        return criteria.stream()
                .anyMatch(c -> CriteriaConstants.isRequired(c.getType()));
    }

    @AssertTrue(message = "Duplicate criteria types are not allowed")
    public boolean hasNoDuplicateCriteriaTypes() {
        if (criteria == null)
            return true;

        Set<String> types = criteria.stream()
                .map(BaseCriteriaRequest::getType)
                .collect(Collectors.toSet());

        return types.size() == criteria.size();
    }

    @AssertTrue(message = "Criteria type must match the provided data")
    public boolean hasValidCriteriaTypeData() {
        if (criteria == null)
            return true;

        return criteria.stream()
                .allMatch(this::isValidCriteriaTypeData);
    }

    private boolean isValidCriteriaTypeData(BaseCriteriaRequest request) {
        if (request == null || request.getType() == null)
            return false;

        return switch (request.getType().toUpperCase()) {
            case CriteriaConstants.AGE -> request.getAgeCriteria() != null;
            case CriteriaConstants.GENDER -> request.getGenderCriteria() != null;
            case CriteriaConstants.SALARY -> request.getSalaryCriteria() != null;
            default -> false;
        };
    }
}