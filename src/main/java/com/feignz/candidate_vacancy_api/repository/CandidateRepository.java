package com.feignz.candidate_vacancy_api.repository;

import com.feignz.candidate_vacancy_api.entity.Candidate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends MongoRepository<Candidate, String> {
    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailAndIdNotIgnoreCase(String email, String id);
}
