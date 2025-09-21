package com.feignz.candidate_vacancy_api.repository;

import com.feignz.candidate_vacancy_api.entity.Vacancy;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacancyRepository extends MongoRepository<Vacancy, String> {
    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameAndIdNotIgnoreCase(String name, String id);
}
