package com.feignz.candidate_vacancy_api.service;

import java.util.List;

import com.feignz.candidate_vacancy_api.dto.response.CandidateRankingResponse;

public interface RankingService {
    List<CandidateRankingResponse> rankCandidatesForVacancy(String vacancyId);
}
