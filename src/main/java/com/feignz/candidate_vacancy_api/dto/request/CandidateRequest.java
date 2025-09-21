package com.feignz.candidate_vacancy_api.dto.request;

import com.feignz.candidate_vacancy_api.entity.Gender;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateRequest {
    private String id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Birthdate is required")
    @Past(message = "Birthdate must be in the past")
    private LocalDate birthdate;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotNull(message = "Current salary is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Current salary must be positive")
    private BigDecimal currentSalary;

}
