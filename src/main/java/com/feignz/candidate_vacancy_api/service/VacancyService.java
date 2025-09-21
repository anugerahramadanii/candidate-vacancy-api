package com.feignz.candidate_vacancy_api.service;

import com.feignz.candidate_vacancy_api.dto.request.VacancyRequest;
import com.feignz.candidate_vacancy_api.dto.response.VacancyResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VacancyService {
    Page<VacancyResponse> findAll(Pageable pageable);

    VacancyResponse findById(String id);

    VacancyResponse create(VacancyRequest request);

    VacancyResponse update(String id, VacancyRequest request);

    void delete(String id);
}
