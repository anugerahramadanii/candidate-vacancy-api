package com.feignz.candidate_vacancy_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateRankingResponse {
    private String candidateId;
    private String name;
    private String email;
    private Integer score;
    private Integer rank;
}