package com.feignz.candidate_vacancy_api.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "candidates")
public class Candidate {
    @Id
    private String id;

    private String name;

    @Indexed(unique = true)
    private String email;

    @Indexed
    private LocalDate birthdate;

    @Indexed
    private Gender gender;

    @Indexed
    private BigDecimal currentSalary;

    public int getAge() {
        return Period.between(birthdate, LocalDate.now()).getYears();
    }
}
