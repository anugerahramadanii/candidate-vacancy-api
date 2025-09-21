package com.feignz.candidate_vacancy_api.service;

import com.feignz.candidate_vacancy_api.dto.request.CandidateRequest;
import com.feignz.candidate_vacancy_api.dto.response.CandidateResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CandidateService {
    Page<CandidateResponse> findAll(Pageable pageable);

    CandidateResponse findById(String id);

    CandidateResponse create(CandidateRequest request);

    CandidateResponse update(String id, CandidateRequest request);

    void delete(String id);
}
