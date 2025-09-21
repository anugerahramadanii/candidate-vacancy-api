package com.feignz.candidate_vacancy_api.controller;

import com.feignz.candidate_vacancy_api.dto.WebResponse;
import com.feignz.candidate_vacancy_api.dto.response.CandidateRankingResponse;
import com.feignz.candidate_vacancy_api.service.RankingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rankings")
@RequiredArgsConstructor
@Slf4j
public class RankingController {
    private final RankingService rankingService;

    @GetMapping("/candidates/{vacancyId}")
    public ResponseEntity<WebResponse<List<CandidateRankingResponse>>> rankCandidatesForVacancy(
            @PathVariable String vacancyId) {

        try {
            if (vacancyId == null || vacancyId.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(WebResponse.<List<CandidateRankingResponse>>builder()
                                .errors(List.of(WebResponse.ErrorDetail.of(
                                        "vacancyId",
                                        "Vacancy ID cannot be null or empty",
                                        "VALIDATION_ERROR")))
                                .build());
            }

            List<CandidateRankingResponse> rankingResults = rankingService.rankCandidatesForVacancy(vacancyId);

            WebResponse<List<CandidateRankingResponse>> response = WebResponse.<List<CandidateRankingResponse>>builder()
                    .data(rankingResults)
                    .build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error ranking candidates for vacancy {}: {}", vacancyId, e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body(WebResponse.<List<CandidateRankingResponse>>builder()
                            .errors(List.of(WebResponse.ErrorDetail.of("An unexpected error occurred")))
                            .build());
        }
    }

}
