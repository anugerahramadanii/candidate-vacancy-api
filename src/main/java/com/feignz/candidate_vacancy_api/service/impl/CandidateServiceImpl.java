package com.feignz.candidate_vacancy_api.service.impl;

import com.feignz.candidate_vacancy_api.dto.request.CandidateRequest;
import com.feignz.candidate_vacancy_api.dto.response.CandidateResponse;
import com.feignz.candidate_vacancy_api.entity.Candidate;
import com.feignz.candidate_vacancy_api.exception.DuplicateDataException;
import com.feignz.candidate_vacancy_api.exception.ResourceNotFoundException;
import com.feignz.candidate_vacancy_api.repository.CandidateRepository;
import com.feignz.candidate_vacancy_api.service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<CandidateResponse> findAll(Pageable pageable) {
        return candidateRepository.findAll(pageable)
                .map(this::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public CandidateResponse findById(String id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("id", "Candidate not found with id: " + id, "NOT_FOUND"));
        return toResponse(candidate);
    }

    @Override
    @Transactional
    public CandidateResponse create(CandidateRequest request) {

        if (candidateRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new DuplicateDataException("email", "Email already exists: " + request.getEmail(), "DUPLICATE");
        }

        Candidate candidate = Candidate.builder()
                .name(request.getName())
                .email(request.getEmail())
                .birthdate(request.getBirthdate())
                .gender(request.getGender())
                .currentSalary(request.getCurrentSalary())
                .build();

        Candidate savedCandidate = candidateRepository.save(candidate);
        return toResponse(savedCandidate);

    }

    @Override
    @Transactional
    public CandidateResponse update(String id, CandidateRequest request) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("id", "Candidate not found with id: " + id, "NOT_FOUND"));

        if (!candidate.getEmail().equals(request.getEmail()) &&
                candidateRepository.existsByEmailAndIdNotIgnoreCase(request.getEmail(), id)) {
            throw new DuplicateDataException("email", "Email already exists: " + request.getEmail(), "DUPLICATE");
        }

        candidate.setName(request.getName());
        candidate.setEmail(request.getEmail());
        candidate.setBirthdate(request.getBirthdate());
        candidate.setGender(request.getGender());
        candidate.setCurrentSalary(request.getCurrentSalary());
        Candidate updatedCandidate = candidateRepository.save(candidate);
        return toResponse(updatedCandidate);

    }

    @Override
    @Transactional
    public void delete(String id) {

        if (!candidateRepository.existsById(id)) {
            throw new ResourceNotFoundException("id", "Candidate not found with id: " + id, "NOT_FOUND");
        }

        candidateRepository.deleteById(id);
    }

    private CandidateResponse toResponse(Candidate candidate) {
        return CandidateResponse.builder()
                .id(candidate.getId())
                .name(candidate.getName())
                .email(candidate.getEmail())
                .birthdate(candidate.getBirthdate())
                .gender(candidate.getGender())
                .currentSalary(candidate.getCurrentSalary())
                .age(candidate.getAge())
                .build();
    }
}
