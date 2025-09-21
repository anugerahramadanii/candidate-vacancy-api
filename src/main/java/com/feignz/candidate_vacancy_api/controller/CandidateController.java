package com.feignz.candidate_vacancy_api.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feignz.candidate_vacancy_api.dto.PagingResponse;
import com.feignz.candidate_vacancy_api.dto.WebResponse;
import com.feignz.candidate_vacancy_api.dto.request.CandidateRequest;
import com.feignz.candidate_vacancy_api.dto.response.CandidateResponse;
import com.feignz.candidate_vacancy_api.service.CandidateService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/candidates")
@RequiredArgsConstructor
public class CandidateController {

        private final CandidateService candidateService;

        @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<WebResponse<List<CandidateResponse>>> getAllCandidates(
                        @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "size", defaultValue = "10") int size) {
                Pageable pageable = PageRequest.of(page, size);
                Page<CandidateResponse> candidatePage = candidateService.findAll(pageable);

                PagingResponse paging = PagingResponse.builder()
                                .currentPage(candidatePage.getNumber())
                                .totalPage(candidatePage.getTotalPages())
                                .size(candidatePage.getSize())
                                .totalElements(candidatePage.getTotalElements())
                                .build();

                WebResponse<List<CandidateResponse>> response = WebResponse.<List<CandidateResponse>>builder()
                                .data(candidatePage.getContent())
                                .paging(paging)
                                .build();

                return ResponseEntity.ok(response);
        }

        @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<WebResponse<CandidateResponse>> getCandidateById(@PathVariable String id) {

                CandidateResponse candidate = candidateService.findById(id);

                WebResponse<CandidateResponse> response = WebResponse.<CandidateResponse>builder()
                                .data(candidate)
                                .build();

                return ResponseEntity.ok(response);
        }

        @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<WebResponse<CandidateResponse>> createCandidate(
                        @Valid @RequestBody CandidateRequest request) {

                CandidateResponse createdCandidate = candidateService.create(request);

                WebResponse<CandidateResponse> response = WebResponse.<CandidateResponse>builder()
                                .data(createdCandidate)
                                .build();

                return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<WebResponse<CandidateResponse>> updateCandidate(
                        @PathVariable String id,
                        @Valid @RequestBody CandidateRequest request) {

                CandidateResponse updatedCandidate = candidateService.update(id, request);

                WebResponse<CandidateResponse> response = WebResponse.<CandidateResponse>builder()
                                .data(updatedCandidate)
                                .build();

                return ResponseEntity.ok(response);
        }

        @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<WebResponse<String>> deleteCandidate(@PathVariable String id) {
                candidateService.delete(id);

                WebResponse<String> response = WebResponse.<String>builder()
                                .data("OK")
                                .build();

                return ResponseEntity.ok(response);
        }

}
