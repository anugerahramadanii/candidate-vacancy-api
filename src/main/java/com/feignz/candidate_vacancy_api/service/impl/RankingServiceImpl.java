package com.feignz.candidate_vacancy_api.service.impl;

import com.feignz.candidate_vacancy_api.dto.response.CandidateRankingResponse;
import com.feignz.candidate_vacancy_api.entity.*;
import com.feignz.candidate_vacancy_api.entity.criteria.*;
import com.feignz.candidate_vacancy_api.exception.ResourceNotFoundException;
import com.feignz.candidate_vacancy_api.repository.CandidateRepository;
import com.feignz.candidate_vacancy_api.repository.VacancyRepository;
import com.feignz.candidate_vacancy_api.service.RankingService;
import com.feignz.candidate_vacancy_api.util.CriteriaConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class RankingServiceImpl implements RankingService {
    private final CandidateRepository candidateRepository;
    private final VacancyRepository vacancyRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CandidateRankingResponse> rankCandidatesForVacancy(String vacancyId) {

        Vacancy vacancy = vacancyRepository.findById(vacancyId)
                .orElseThrow(() -> new ResourceNotFoundException("id", "Vacancy not found with id: " + vacancyId,
                        "NOT_FOUND"));

        List<Candidate> candidates = candidateRepository.findAll();

        List<CandidateRankingResponse> rankedCandidates = new ArrayList<>();

        for (Candidate candidate : candidates) {
            int score = calculateCandidateScore(candidate, vacancy.getCriteria());

            CandidateRankingResponse response = CandidateRankingResponse.builder()
                    .candidateId(candidate.getId())
                    .name(candidate.getName())
                    .email(candidate.getEmail())
                    .score(score)
                    .build();

            rankedCandidates.add(response);
        }

        rankedCandidates.sort((a, b) -> {
            int scoreComparison = Integer.compare(b.getScore(), a.getScore());
            if (scoreComparison != 0) {
                return scoreComparison;
            }

            return a.getName().compareTo(b.getName());
        });

        for (int i = 0; i < rankedCandidates.size(); i++) {
            rankedCandidates.get(i).setRank(i + 1);
        }

        log.info("Ranked {} candidates for vacancy: {}", rankedCandidates.size(), vacancyId);
        return rankedCandidates;
    }

    private int calculateCandidateScore(Candidate candidate, List<Criteria> criteria) {
        int totalScore = 0;

        log.debug("Calculating score for candidate: {} (age: {}, gender: {}, salary: {})",
                candidate.getName(), candidate.getAge(), candidate.getGender(), candidate.getCurrentSalary());

        for (Criteria criterion : criteria) {
            try {
                boolean matches = matchesCriteria(candidate, criterion);

                if (matches) {
                    totalScore += criterion.getWeight();
                    log.debug("Candidate {} matched {} criteria, added weight: {} (total: {})",
                            candidate.getName(), criterion.getType(), criterion.getWeight(), totalScore);
                } else {
                    log.debug("Candidate {} did NOT match {} criteria (weight: {})",
                            candidate.getName(), criterion.getType(), criterion.getWeight());
                }
            } catch (Exception e) {
                log.warn("Error matching criteria {} for candidate {}: {}",
                        criterion.getType(), candidate.getId(), e.getMessage());
            }
        }

        log.debug("Final score for {}: {}", candidate.getName(), totalScore);
        return totalScore;
    }

    private boolean matchesCriteria(Candidate candidate, Criteria criterion) {
        return switch (criterion.getType()) {
            case CriteriaConstants.AGE -> matchesAgeCriteria(candidate, (AgeCriteria) criterion);
            case CriteriaConstants.GENDER -> matchesGenderCriteria(candidate, (GenderCriteria) criterion);
            case CriteriaConstants.SALARY -> matchesSalaryCriteria(candidate, (SalaryRangeCriteria) criterion);
            default -> {
                log.warn("Unsupported criteria type: {}", criterion.getType());
                yield false;
            }
        };
    }

    private boolean matchesAgeCriteria(Candidate candidate, AgeCriteria ageCriteria) {
        int candidateAge = candidate.getAge();
        return candidateAge >= ageCriteria.getMinAge() && candidateAge <= ageCriteria.getMaxAge();
    }

    private boolean matchesGenderCriteria(Candidate candidate, GenderCriteria genderCriteria) {
        return genderCriteria.getGender() == Gender.ANY ||
                genderCriteria.getGender() == candidate.getGender();
    }

    private boolean matchesSalaryCriteria(Candidate candidate, SalaryRangeCriteria salaryCriteria) {
        return candidate.getCurrentSalary().compareTo(salaryCriteria.getMaxSalary()) <= 0;
    }

}
