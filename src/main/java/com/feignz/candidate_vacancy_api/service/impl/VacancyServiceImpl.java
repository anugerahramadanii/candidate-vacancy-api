package com.feignz.candidate_vacancy_api.service.impl;

import com.feignz.candidate_vacancy_api.dto.request.VacancyRequest;
import com.feignz.candidate_vacancy_api.dto.request.BaseCriteriaRequest;
import com.feignz.candidate_vacancy_api.dto.response.VacancyResponse;
import com.feignz.candidate_vacancy_api.entity.Vacancy;
import com.feignz.candidate_vacancy_api.entity.criteria.Criteria;
import com.feignz.candidate_vacancy_api.exception.ResourceNotFoundException;
import com.feignz.candidate_vacancy_api.exception.DuplicateDataException;
import com.feignz.candidate_vacancy_api.repository.VacancyRepository;
import com.feignz.candidate_vacancy_api.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {

    private final VacancyRepository vacancyRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<VacancyResponse> findAll(Pageable pageable) {
        return vacancyRepository.findAll(pageable)
                .map(this::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public VacancyResponse findById(String id) {
        Vacancy vacancy = vacancyRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("id", "Vacancy not found with id: " + id, "NOT_FOUND"));
        return toResponse(vacancy);
    }

    @Override
    @Transactional
    public VacancyResponse create(VacancyRequest request) {

        if (vacancyRepository.existsByNameIgnoreCase(request.getName())) {
            throw new DuplicateDataException("name", "Vacancy name already exists: " + request.getName(), "DUPLICATE");
        }

        List<Criteria> criteria = request.getCriteria().stream()
                .map(BaseCriteriaRequest::toEntity)
                .toList();

        Vacancy vacancy = Vacancy.builder()
                .name(request.getName())
                .criteria(criteria)
                .build();

        Vacancy savedVacancy = vacancyRepository.save(vacancy);
        return toResponse(savedVacancy);
    }

    @Override
    @Transactional
    public VacancyResponse update(String id, VacancyRequest request) {

        Vacancy vacancy = vacancyRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("id", "Vacancy not found with id: " + id, "NOT_FOUND"));

        if (!vacancy.getName().equals(request.getName()) &&
                vacancyRepository.existsByNameAndIdNotIgnoreCase(request.getName(), id)) {
            throw new DuplicateDataException("name", "Vacancy name already exists: " + request.getName(), "DUPLICATE");
        }

        List<Criteria> criteria = request.getCriteria().stream()
                .map(BaseCriteriaRequest::toEntity)
                .toList();

        vacancy.setName(request.getName());
        vacancy.setCriteria(criteria);

        Vacancy updatedVacancy = vacancyRepository.save(vacancy);
        return toResponse(updatedVacancy);
    }

    @Override
    @Transactional
    public void delete(String id) {
        if (!vacancyRepository.existsById(id)) {
            throw new ResourceNotFoundException("id", "Vacancy not found with id: " + id, "NOT_FOUND");
        }

        vacancyRepository.deleteById(id);
    }

    private VacancyResponse toResponse(Vacancy vacancy) {
        return VacancyResponse.builder()
                .id(vacancy.getId())
                .name(vacancy.getName())
                .criteria(vacancy.getCriteria())
                .build();
    }

}
