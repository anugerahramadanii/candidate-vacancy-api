package com.feignz.candidate_vacancy_api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.feignz.candidate_vacancy_api.entity.criteria.Criteria;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "vacancies")
public class Vacancy {
    @Id
    private String id;

    private String name;

    private List<Criteria> criteria;
}
