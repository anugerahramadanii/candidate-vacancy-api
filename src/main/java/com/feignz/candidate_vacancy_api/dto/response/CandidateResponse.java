package com.feignz.candidate_vacancy_api.dto.response;

import com.feignz.candidate_vacancy_api.entity.Gender;
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
public class CandidateResponse {
    private String id;
    private String name;
    private String email;
    private LocalDate birthdate;
    private Gender gender;
    private BigDecimal currentSalary;
    private Integer age;
}