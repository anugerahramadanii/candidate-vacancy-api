package com.feignz.candidate_vacancy_api.controller;

import com.feignz.candidate_vacancy_api.dto.PagingResponse;
import com.feignz.candidate_vacancy_api.dto.WebResponse;
import com.feignz.candidate_vacancy_api.dto.request.VacancyRequest;
import com.feignz.candidate_vacancy_api.dto.response.VacancyResponse;
import com.feignz.candidate_vacancy_api.service.VacancyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vacancies")
@RequiredArgsConstructor
public class VacancyController {

        private final VacancyService vacancyService;

        @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<WebResponse<List<VacancyResponse>>> getAllVacancies(
                        @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "size", defaultValue = "10") int size) {

                Pageable pageable = PageRequest.of(page, size);
                Page<VacancyResponse> vacancyPage = vacancyService.findAll(pageable);

                PagingResponse paging = PagingResponse.builder()
                                .currentPage(vacancyPage.getNumber())
                                .totalPage(vacancyPage.getTotalPages())
                                .size(vacancyPage.getSize())
                                .totalElements(vacancyPage.getTotalElements())
                                .build();

                WebResponse<List<VacancyResponse>> response = WebResponse.<List<VacancyResponse>>builder()
                                .data(vacancyPage.getContent())
                                .paging(paging)
                                .build();

                return ResponseEntity.ok(response);
        }

        @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<WebResponse<VacancyResponse>> getVacancyById(@PathVariable String id) {

                VacancyResponse vacancy = vacancyService.findById(id);

                WebResponse<VacancyResponse> response = WebResponse.<VacancyResponse>builder()
                                .data(vacancy)
                                .build();

                return ResponseEntity.ok(response);
        }

        @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<WebResponse<VacancyResponse>> createVacancy(@Valid @RequestBody VacancyRequest request) {

                VacancyResponse createdVacancy = vacancyService.create(request);

                WebResponse<VacancyResponse> response = WebResponse.<VacancyResponse>builder()
                                .data(createdVacancy)
                                .build();

                return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<WebResponse<VacancyResponse>> updateVacancy(
                        @PathVariable String id,
                        @Valid @RequestBody VacancyRequest request) {

                VacancyResponse updatedVacancy = vacancyService.update(id, request);

                WebResponse<VacancyResponse> response = WebResponse.<VacancyResponse>builder()
                                .data(updatedVacancy)
                                .build();

                return ResponseEntity.ok(response);
        }

        @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<WebResponse<String>> deleteVacancy(@PathVariable String id) {
                vacancyService.delete(id);

                WebResponse<String> response = WebResponse.<String>builder()
                                .data("OK")
                                .build();

                return ResponseEntity.ok(response);
        }
}
